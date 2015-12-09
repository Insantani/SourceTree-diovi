<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class ArticleTagsModel extends Model
{
    //
    protected $table='article_tags';
    protected $primaryKey ="article_id";
    public function article_tags(){
        
        return $this->belongsTo('App\ArticleModel');
        
    }
    
    public function tagArticles(){
        return $this->belongsTo('App\TagsModel');
    }
    
    public function article (){
        return $this->hasOne('App\ArticleModel','article_id');
    }
}
