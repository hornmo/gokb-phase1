package Register

import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.ui.RegistrationCode
import org.gokb.cred.Org

class RegisterController extends grails.plugins.springsecurity.ui.RegisterController {

    def index = {
        def copy = [:] + (flash.chainedParams ?: [:])
        copy.remove 'controller'
        copy.remove 'action'
        def orgs = Org.executeQuery("select o.id, o.name from Org as o")
        [command: new RegisterCommand(copy), orgs: orgs]
    }

    def register = { RegisterCommand command ->

        if (command.hasErrors()) {
            def orgs = Org.executeQuery("select o.id, o.name from Org as o")
            render view: 'index', model: [command: command, orgs: orgs]
            return
        }

        def org = Org.get(command.orgName)

        String salt = saltSource instanceof NullSaltSource ? null : command.username
        def user = lookupUserClass().newInstance(org: org, email: command.email, username: command.username,
                accountLocked: true, enabled: true)

        RegistrationCode registrationCode = springSecurityUiService.register(user, command.password, salt)
        if (registrationCode == null || registrationCode.hasErrors()) {
            // null means problem creating the user
            flash.error = message(code: 'spring.security.ui.register.miscError')
            flash.chainedParams = params
            redirect action: 'index'
            return
        }

        String url = generateLink('verifyRegistration', [t: registrationCode.token])

        def conf = SpringSecurityUtils.securityConfig
        def body = conf.ui.register.emailBody
        if (body.contains('$')) {
            body = evaluate(body, [user: user, url: url])
        }
        mailService.sendMail {
            to command.email
            from conf.ui.register.emailFrom
            subject conf.ui.register.emailSubject
            html body.toString()
        }

        render view: 'index', model: [emailSent: true]
    }
}

class RegisterCommand {

    String username
    String email
    String password
    String password2
    String orgName

    def grailsApplication

    static constraints = {
        username blank: false, nullable: false, validator: { value, command ->
            if (value) {
                def User = command.grailsApplication.getDomainClass(
                        SpringSecurityUtils.securityConfig.userLookup.userDomainClassName).clazz
                if (User.findByUsername(value)) {
                    return 'registerCommand.username.unique'
                }
            }
        }
        email blank: false, nullable: false, email: true
        password blank: false, nullable: false, validator: RegisterController.passwordValidator
        password2 validator: RegisterController.password2Validator
        orgName blank: false, nullable: false, validator: { value, command ->
            if (value) {
                if (!Org.get(value)) {
                    return 'user.org.registerCommand'
                }
            }
        }
    }
}

