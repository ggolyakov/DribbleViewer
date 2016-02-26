package com.woolf.dribbleviewer.db;

import android.provider.BaseColumns;

public interface DatabaseKeys extends BaseColumns {
    String SHOTS_TABLE = "shots_table";

    String ID_SHOT      = "id_shot";
    String TITLE        = "title";
    String DESCRIPTION  = "description";
    String VIEWS        = "views";
    String LIKES        = "likes";
    String COMMENTS     = "comments";
    String IMG_HI_DPI   = "img_hi_dpi";
    String IMG_NORMAL   = "img_normal";
    String IMG_TEASER   = "img_teaser";
    String IMG_HEIGHT   = "img_height";
    String IMG_WIDTH    = "img_width";


    /**
     * Create shots table
     */
    String CREATE_SHOTS_TABLE = String.format(
            "CREATE TABLE %1$s (" +                                         //SHOTS_TABLE
                    " %2$s INTEGER PRIMARY KEY  AUTOINCREMENT" +            //_ID
                    ",%3$s INTEGER" +                                       // ID_SHOT
                    ",%4$s VARCHAR(255)" +                                  //TITLE
                    ",%5$s TEXT" +                                          //DESCRIPTION
                    ",%6$s INTEGER" +                                       //VIEWS
                    ",%7$s INTEGER" +                                       //LIKES
                    ",%8$s INTEGER" +                                       //COMMENTS
                    ",%9$s VARCHAR(255)" +                                   //IMG_HI_DPI
                    ",%10$s VARCHAR(255)" +                                  //IMG_NORMAL
                    ",%11$s VARCHAR(255)" +                                 //IMG_TEASER
                    ",%12$s INTEGER" +                                      //IMG_HEIGHT
                    ",%13$s INTEGER" +                                      //IMG_WIDTH
                    ")"
            , SHOTS_TABLE
            , _ID
            , ID_SHOT
            , TITLE
            , DESCRIPTION
            , VIEWS
            , LIKES
            , COMMENTS
            , IMG_HI_DPI
            , IMG_NORMAL
            , IMG_TEASER
            , IMG_HEIGHT
            , IMG_WIDTH
    );
}
