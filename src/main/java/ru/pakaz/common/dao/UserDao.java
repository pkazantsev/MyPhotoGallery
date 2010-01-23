package ru.pakaz.common.dao;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import ru.pakaz.common.model.User;

/**
 * Класс управления пользователями
 * 
 * @author wilson
 *
 */
public class UserDao extends HibernateDaoSupport {
    static private Logger logger = Logger.getLogger( UserDao.class );

    public User getUserById( int userId ) {
        List<User> users;

        users = getHibernateTemplate().find( "FROM User WHERE id = ?", userId );

        if( users != null && users.size() > 0 ) {
            return users.get(0);
        }
        else {
            logger.debug( "UserDao.getUserByLogin: user not found!" );
            return null;
        }
    }
    
    public User getUserByLogin( String login ) {
        List<User> users;

        if( login == null ) {
            logger.debug( "UserDao.getUserByLogin: Login is null!" );
            return null;
        }
        users = getHibernateTemplate().find( "FROM User WHERE login = ?", login );

        if( users != null && users.size() > 0 ) {
            return users.get(0);
        }
        else {
            logger.debug( "UserDao.getUserByLogin: user not found!" );
            return null;
        }
    }

    public User getUserFromSession( HttpServletRequest request ) {
        return (User)request.getSession(true).getAttribute( "User" );
    }

    public void setUserToSession(HttpServletRequest request, Object user) {
        request.getSession(true).setAttribute( "User", user );
    }

    /**
     * Сохранение нового пользователя в базу
     * 
     * @param request
     * @param user
     */
    public void createUser(HttpServletRequest request, User user) {
        try {
            getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
            getHibernateTemplate().save( user );
        }
        catch( DataAccessException e ) {
            e.printStackTrace();
        }
    }

    /**
     * Обновление персональной информации о пользователе
     * 
     * @param request
     * @param user
     */
    public void updateUser(HttpServletRequest request, User user) {
        try {
            getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
            getHibernateTemplate().update( user );
            this.setUserToSession( request, user );
        }
        catch( DataAccessException e ) {
            e.printStackTrace();
        }
    }

    /**
     * Объявление пользователя удаленным (без фактического удаления из базы)
     */
    public void deleteUser(HttpServletRequest request, User user) {
/*        try {
            getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
            getHibernateTemplate().delete(user);
        }
        catch( DataAccessException e ) {
            e.printStackTrace();
        }*/
    }
}