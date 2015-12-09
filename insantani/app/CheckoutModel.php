<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use App\User;

class CheckoutModel extends Model
{
    //
    protected $table="checkingout";
    protected $primaryKey="checkingout_id";
    protected $fillable=['address','user_id','product_id','productQty','status'];
    
    public function user(){
         return $this->belongsTo('User');
    }
}
