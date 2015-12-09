<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class UserRefreshTokenModel extends Model
{
    //
    protected $table="oauth_refresh_tokens";
    protected $primaryKey="refresh_token";
    protected $visible=['refresh_token','expires'];
}
