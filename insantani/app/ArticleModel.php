<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class ArticleModel extends Model
{
    //
    protected $table='article';
    protected $primaryKey ="article_id";
    protected $hidden=['article_filename','article_filepath'];
    
    public function article_tags(){
        
        return $this->hasMany('App\ArticleTagsModel','article_id');
 
       
 
    }
   
    
    
}
