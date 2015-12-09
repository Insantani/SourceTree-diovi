<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class TagsModel extends Model
{
    //
    protected $table='tags';
    protected $primaryKey ="tags";
    
    public function tagArticles(){
        
        return $this->hasMany('App\ArticleTagsModel','tags');
    }
}
