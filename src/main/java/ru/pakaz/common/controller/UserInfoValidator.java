package ru.pakaz.common.controller;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import ru.pakaz.common.dao.UserDao;
import ru.pakaz.common.model.User;
import org.springframework.validation.Validator;

public class UserInfoValidator implements Validator {
    static private Logger logger = Logger.getLogger( UserInfoValidator.class );
    private UserDao usersManager;
    
    public boolean supports(Class clazz) {
        return clazz.equals( User.class );
    }

    public void validate(Object command, Errors errors) {
        User user = (User) command;
        if( user == null ) {
            return;
        }
/*
        String nickName  = user.getNickName();
        String firstName = user.getFirstName();
        String lastName  = user.getLastName();*/
        String email     = user.getEmail();
        
        /*if( nickName == null || nickName.length() == 0 ) {
            errors.reject( "error.nickname.tooShort" );
        }*/
        if( email == null || email.length() == 0 ) {
            logger.error( "Ошибка валидации e-mail! адрес пустой!" );
            errors.rejectValue( "email", "error.user.email.tooShort" );
        }
        else if( email.matches( "^(([A-Za-z0-9!#$%&amp;'*+/=?^_`{|}~-][A-Za-z0-9!#$%&amp;'*+/=?^_`{|}~\\.-]{0,63})|(\"[^(\\|\")]{0,255}\"))$" ) ) {
            logger.error( "Ошибка валидации e-mail! адрес не соответствует маске!" );
            errors.rejectValue( "email", "error.user.email.formatError" );
        }
    }
    
    public void setUsersManager( UserDao usersManager ) {
        this.usersManager = usersManager;
    }
}
