<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class NutritionFactsModel extends Model
{
//    public static $foreign_key = 'product_id';
    //
    protected $table="nutrition_fact";
    public function product(){
        
        
        return $this->belongsTo('ProductModel');
    }
}
