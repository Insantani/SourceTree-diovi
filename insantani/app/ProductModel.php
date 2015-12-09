<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use App\NutritionFactsModel;
use App\Farmer;




class ProductModel extends Model
    
{

    protected $table="product";
    protected $hidden=["updated_at","product_filename","product_filepath"];
   
    public function nutritionFacts(){
        
        return $this->hasMany('App\NutritionFactsModel','product_id');
 
       
 
    }
    public function index(){
        
        return $this->belongsTo('App\Farmer','farmer_username');
    }

    
   
    
}
