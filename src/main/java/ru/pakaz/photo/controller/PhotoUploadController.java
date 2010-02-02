package ru.pakaz.photo.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;
import ru.pakaz.common.dao.UserDao;
import ru.pakaz.common.model.User;
import ru.pakaz.photo.dao.AlbumDao;
import ru.pakaz.photo.dao.PhotoDao;
import ru.pakaz.photo.model.Album;
import ru.pakaz.photo.model.Photo;
import ru.pakaz.photo.model.PhotoFile;
import ru.pakaz.photo.service.PhotoFileService;

@Controller
public class PhotoUploadController {
    private Logger logger = Logger.getLogger( PhotoUploadController.class );

    @Autowired
    private UserDao usersManager;
    @Autowired
    private AlbumDao albumsManager;
    @Autowired
    private PhotoDao photoManager;
    @Autowired
    private PhotoFileService photoFileService;

    /**
     * Загрузка фотографии в определенный параметром albumId альбом
     * 
     * @param albumId
     * @param request
     * @return
     */
/*
    @RequestMapping(value = "/album_{albumId}/upload.html", method = RequestMethod.GET)
    public ModelAndView getWithAlbum( @PathVariable("albumId") int albumId, HttpServletRequest request ) {
        ModelAndView mav = new ModelAndView( "uploadPhoto" );
        Album album = this.albumsManager.getAlbumsById( albumId );
        mav.addObject( "album", album );
        mav.addObject( "pageName", new RequestContext(request).getMessage( "page.title.createAlbum" ) );

        return mav;
    }
*/
    /**
     * Загрузка фотографии в определенный параметром albumId альбом
     * 
     * @param albumId
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload.html", method = RequestMethod.GET)
    public ModelAndView get( HttpServletRequest request ) {
        ModelAndView mav = new ModelAndView( "uploadPhoto" );
        ArrayList<Album> albums = this.albumsManager.getAlbumsByUser( this.usersManager.getUserFromSession( request ) );
        mav.addObject( "albums", albums );
        mav.addObject( "pageName", new RequestContext(request).getMessage( "page.title.createAlbum" ) );

        return mav;
    }

    /**
     * Загрузка фотографии во временный альбом
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload.html", method = RequestMethod.POST)
    public ModelAndView upload( HttpServletRequest request, HttpServletResponse response, 
            @RequestParam("file") MultipartFile file  ) {

        if( !file.isEmpty() ) {
            this.logger.debug( "File is not empty" );

            try {
                Photo newPhoto = new Photo();
                newPhoto.setUser( this.usersManager.getUserFromSession( request ) );
                newPhoto.setFileName( file.getOriginalFilename() );
                newPhoto.setTitle( file.getOriginalFilename() );

                PhotoFile original = this.photoFileService.saveOriginal( file.getBytes() );
                original.setParentPhoto( newPhoto );
                
                newPhoto.setPhotoFile( original );
                this.photoManager.createPhoto( newPhoto );
                
                PhotoFile bigPhotoFile = this.photoFileService.scalePhoto( original, 640 );
                newPhoto.setPhotoFile( bigPhotoFile );

            }
            catch( IOException e ) {
                this.logger.debug( "Exception during reading sent file!" );
                e.printStackTrace();
            }
        }
        else {
            this.logger.debug( "File is empty" );
        }

        try {
            ModelAndView mav;
            mav = new ModelAndView( "uploadPhoto" );
            return mav;
        }
        catch( Exception e ) {
            this.logger.debug( "JUST ERROR!" );
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * 
     * @param albumId
     * @param result
     * @param request
     * @return
     */
    /*
    @RequestMapping(value = "/album_{albumId}/upload.html", method = RequestMethod.POST)  
    public ModelAndView uploadWithAlbum( @PathVariable("albumId") int albumId, HttpServletRequest request, 
            HttpServletResponse response, @RequestParam("file") MultipartFile file ) {
        File galleryPath = new File( request.getSession().getServletContext().getInitParameter( "catalog" ), "tmp" );

        if( !file.isEmpty() ) {
            this.logger.debug( "File is not empty" );

            try {
                this.uploadPhoto( galleryPath, file.getBytes() );
            }
            catch( IOException e ) {
                this.logger.debug( "Exeption during reading sent file!" );
                e.printStackTrace();
            }
        }
        else {
            this.logger.debug( "File is empty" );
        }

        try {
            ModelAndView mav;
            mav = new ModelAndView( "uploadPhoto" );
            return mav;
        }
        catch( Exception e ) {
            this.logger.debug( "JUST ERROR!" );
            e.printStackTrace();
            return null;
        }
    }
*/

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

    public PhotoDao getPhotoDao() {
        return this.photoManager;
    }
    public void setUserDao( PhotoDao photoDao ) {
        this.photoManager = photoDao;
    }

    public PhotoFileService getPhotoFileService() {
        return this.photoFileService;
    }
    public void setUserDao( PhotoFileService photoFileService ) {
        this.photoFileService = photoFileService;
    }
}
