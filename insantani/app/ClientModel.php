<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class ClientModel extends Model
{
    //
    protected $table="oauth_clients";
    protected $primaryKey="client_id";
    
    protected $hidden = ['client_secret'];
    
    protected $fillable = ['client_id', 'client_secret', 'redirect_uri','grant_types','scope','user_id'];
}
