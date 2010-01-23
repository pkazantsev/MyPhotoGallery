package ru.pakaz.photo.controller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;
import ru.pakaz.common.dao.UserDao;
import ru.pakaz.common.model.User;
import ru.pakaz.photo.dao.AlbumDao;
import ru.pakaz.photo.model.Album;

@Controller
public class AlbumsListController {
    static private Logger logger = Logger.getLogger( AlbumsListController.class );

    @Autowired
    private UserDao usersManager;
    @Autowired
    private AlbumDao albumsManager;
    
    /**
     * Метод подготавливает к выводу список альбомов текущего пользователя
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/albumsList.html", method = RequestMethod.GET)
    public ModelAndView showList( HttpServletRequest request ) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName( "albumsList" );

        User user = this.usersManager.getUserFromSession( request );
        ArrayList<Album> albums = this.albumsManager.getAlbumsByUser( user );
        logger.debug( "We got albums list" );
        if( albums != null ) {
            logger.debug( "Albums list is not null and have "+ albums.size() +" elements" );
            mav.addObject( "albums", albums );
            mav.addObject( "albumsCount", albums.size() );
        }

        String title = new RequestContext(request).getMessage( "page.title.albumsList" );
        logger.debug( "Adding title string to the view: '"+ title +"'" );

        mav.addObject( "pageName", title );

        return mav;
    }
    
    /**
     * Метод подготавливает к выводу спсиок альбомов указанного 
     * в параметре albumId пользователя
     * 
     * @param albumId
     * @param request
     * @return
     */
    @RequestMapping(value = "/album/(*:albumId).html", method = RequestMethod.GET)
    public ModelAndView showAlbum( @RequestParam("albumId") int albumId, HttpServletRequest request ) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName( "viewAlbum" );

        Album album = this.albumsManager.getAlbumsById( albumId );
        logger.debug( "We got albums list" );
        if( album != null ) {
            logger.debug( "We have album \""+ album.getTitle() +"\"" );
            mav.addObject( "currentAlbum", album );
        }

        String title = new RequestContext(request).getMessage( "page.title.viewAlbum" );
        logger.debug( "Adding title string to the view: '"+ title +"'" );

        mav.addObject( "pageName", title +" "+ album.getTitle() );

        return mav;
    }

    public AlbumDao getAlbumDao() {
        return this.albumsManager;
    }
    public void setAlbumDao( AlbumDao albumDao ) {
        this.albumsManager = albumDao;
    }

    public UserDao getUserDao() {
        return this.usersManager;
    }
    public void setUserDao( UserDao userDao ) {
        this.usersManager = userDao;
    }
}