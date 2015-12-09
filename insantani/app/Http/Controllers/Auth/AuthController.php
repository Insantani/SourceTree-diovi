<?php

namespace App\Http\Controllers\Auth;

use App\User;
use Validator;
use Hash;
use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Foundation\Auth\ThrottlesLogins;
use Illuminate\Foundation\Auth\AuthenticatesAndRegistersUsers;


class AuthController extends Controller
{
    /*
    |--------------------------------------------------------------------------
    | Registration & Login Controller
    |--------------------------------------------------------------------------
    |
    | This controller handles the registration of new users, as well as the
    | authentication of existing users. By default, this controller uses
    | a simple trait to add these behaviors. Why don't you explore it?
    |
    */

    use AuthenticatesAndRegistersUsers, ThrottlesLogins;

    /**
     * Create a new authentication controller instance.
     *
     * @return void
     */
//    protected $redirectTo='/';
    public function __construct()
    {
//        $this->middleware('guest', ['except' => 'getLogout']);
    }

    /**
     * Get a validator for an incoming registration request.
     *
     * @param  array  $data
     * @return \Illuminate\Contracts\Validation\Validator
     */
    protected function validator(array $data)
    {
        return Validator::make($data, [
            'name' => 'required|max:255',
            'email' => 'required|email|max:255|unique:users',
            'password' => 'required|confirmed|max:50',
            'address'=>'required|max:100',
            'phone_number'=>'required|max:15'
//            'password_confirmation' => 'required_with:password|max:50'
        ]);
    }

    /**
     * Create a new user instance after a valid registration.
     *
     * @param  array  $data
     * @return User
     */
    protected function create(array $data)
    {
        
//        print ($data);
        $todo= User::create([
            'user_id'=>uniqid($data['email'],true),
            'name' => $data['name'],
            'email' => $data['email'],
            'password' => bcrypt($data['password']),
            'phone_number'=>$data['phone_number'],
            'address'=>$data['address']
            
        ]);
        $todo->save();
        return $todo;
    }
    
    public function postRegister(Request $request)
    {
        $validator = $this->validator($request->all());

        if ($validator->fails()) {
            echo($validator->messages());
        }else{

            $this->create($request->all());

            return response()->json([ 'message' => 'Registration Complete!' ], 201);
        }
    }
    
    public function userInfo(Request $request){
        $user_id=$request->input('user_id');
        $todos=User::find($user_id);
        if (count($todos)>0){
            return $todos;
        }else{
            return response("Not Found",404);
        }
        
        
    }
//    public function postLogin(Request $request)
//    {
//
//        $data = $request->all();
//        $todo = User::where('email', '=', $data['email']);
//        if(count($todo) > 0 && Hash::check($data['password'], $todo -> pluck('password'))){
//            return response()->json(['message' => 'Login complete!'], 201);
//        } else {
//            return response()->json(['message' => 'Login failed!'], 403);
//        }
//
//
//        
//    }

    

}
