package ru.pakaz.photo.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pakaz.photo.model.PhotoFile;

@Transactional
@Repository
public class PhotoFileDao extends HibernateDaoSupport {
    static private Logger logger = Logger.getLogger( PhotoFileDao.class );

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings( "unchecked" )
    public PhotoFile getFileById( int fileId ) {
        List<PhotoFile> filesList;

        filesList = sessionFactory.getCurrentSession()
            .createQuery("FROM PhotoFile WHERE id = ? and deleted = false")
            .setInteger(0, fileId)
            .list();
/*
        filesList = getHibernateTemplate().find( "FROM PhotoFile WHERE id = ?", fileId );
*/
        if( filesList != null ) {
            return filesList.get(0);
        }
        else {
            logger.debug( "File not found!" );
            return null;
        }
    }
    
    public Long getTotalPhotosCount() {
        Long result = null;

        if( sessionFactory.getCurrentSession() == null )
            return null;
        
        Object res = sessionFactory.getCurrentSession()
                .createQuery("SELECT COUNT(distinct parentPhoto) FROM PhotoFile")
                .uniqueResult();

        if( res != null ) {
            result = (Long)res;
        }

        return result;
    }
    
    public Long getTotalPhotosSize() {
        Long result = null;
        
        if( sessionFactory.getCurrentSession() == null )
            return null;

        Object res = sessionFactory.getCurrentSession()
                .createQuery("SELECT SUM(filesize) FROM PhotoFile")
                .uniqueResult();

        if( res != null ) {
            result = (Long)res;
        }

        return result;
    }
    
    @Transactional
    public void createFile( PhotoFile file ) {
        getHibernateTemplate().setFlushMode( HibernateTemplate.FLUSH_ALWAYS );
        getHibernateTemplate().save( file );
        getHibernateTemplate().flush();
/*
        Session sess = sessionFactory.getCurrentSession();
        sess.setFlushMode( FlushMode.ALWAYS );
        sess.save(file);
        sess.flush();
*/
    }

    @Transactional
    public void deleteFile( PhotoFile file ) {
//        getHibernateTemplate().setFlushMode( HibernateTemplate.FLUSH_ALWAYS );
//        getHibernateTemplate().delete( file );
    }

    @Transactional
    public void updateFile( PhotoFile file ) {
        getHibernateTemplate().setFlushMode( HibernateTemplate.FLUSH_ALWAYS );
        getHibernateTemplate().update(file);
        getHibernateTemplate().flush();
    }
}
