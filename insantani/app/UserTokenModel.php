<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class UserTokenModel extends Model
{
    //
    protected $table="oauth_access_tokens";
    protected $primaryKey="access_token";
    protected $visible=['access_token','expires'];
}
