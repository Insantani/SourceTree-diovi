<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use App\User;

class ShoppingCartModel extends Model
{
    //
    protected $table="shopping_cart";
    protected $fillable=['product_id','user_id','product_quantity'];
    
    public function user(){
         return $this->belongsTo('User');
    }
}
