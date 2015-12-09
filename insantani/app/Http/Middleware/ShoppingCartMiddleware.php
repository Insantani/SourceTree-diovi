<?php

namespace App\Http\Middleware;

use Closure;
use App\UserTokenModel;
use App\UserRefreshTokenModel;
use OAuth2\GrantType;

class ShoppingCartMiddleware
{
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle($request, Closure $next)
    {
        $access_token=$request->header('access_token');
        $refresh_token=$request->header('refresh_token');
        if($access_token==null && $refresh_token==null){
            
            return response('No Token',400);
            
        }else{
            $verifyAccess=UserTokenModel::find($access_token);
            $verifyRefresh=UserRefreshTokenModel::find($refresh_token);
            $current_time=time();
//            echo(strtotime($verifyAccess->expires)-$current_time .'<br>');
//            echo($current_time);
//            echo(((strtotime($verifyAccess->expires)-$current_time)<3600)."<br>");
//            echo((strtotime($verifyRefresh->expires)-$current_time));
            if(count($verifyAccess)>0){
                if((strtotime($verifyAccess->expires)>$current_time)){
                    return $next($request);
                }else{
                    return response()->json(['message'=>'Token expired'],403);
                }
                
            }else{
                return response()->json(['message'=>'Invalid token'],403);
            }
        }
    }
}
