<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\CheckoutModel;
use App\ShoppingCartModel;
//use App\UserTokenModel;
use App\ProductModel;
use Mail;
use App\User;
use App\Http\Requests;
use App\Http\Controllers\Controller;

class CheckoutController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     
     */
    
   
    public function index()
    {
        //
    }
    public function createCheckOut(Request $req)
    {
        //
//     
            $items=$req->all();
        
                
//          
            $failed=array();
            $data=$items['data'];
            for ($i=0; $i<count($data);$i++){
                $todo=CheckoutModel::create([
                    'address'=>$data[$i]['address'],
                    'user_id'=>$data[$i]['user_id'],
                    'product_id'=>$data[$i]['product_id'],
                    'productQty'=>$data[$i]['productQty'],
                    'status'=>'pending'
                    ]);
                $todo2=ProductModel::find($data[$i]['product_id']);
                if((($todo2->stock_num)-$data[$i]['productQty'])>=0){
                    $todo2->stock_num=($todo2->stock_num)-$data[$i]['productQty'];
                    $todo2->save();
                    $todo->save();
                    $todos=ShoppingCartModel::where("user_id","=",$data[$i]['user_id'])
                                ->where("product_id","=",$data[$i]['product_id']);
                    $todos->delete();
                }else{
                    array_push($failed,$data[$i]);
                }
                
            
                
            }
            if(count($failed)==0){
                return response()->json(['message'=>'success','state'=>'check out'],201);
            }else{
                return response()->json(['message'=>'out of stocks',
                                         'state'=>'check out',
                                         'data'=>$failed],400);
            }
            
//            
    }
    public function changeStatus(Request $req,$id)
    {
        //
//          
            $segments=explode('/',$id);
            $data=$req->all();
            $todos=CheckoutModel::find($segments[0]);
            if($todos->status!=$data['status']){
                $todos->status=$data['status'];
//                $todos->save();
                
                
                $user=User::find($todos->user_id);
                if(count($user)>0){
                    
                    Mail::send('insantani1', $data, function($message) use ($user)
                    {
                        $message->to($user->email, $user->name)
                                ->subject('status report!');
                    });
                    $todos->save();
                    return response()->json(['message'=>'success','state'=>'change status'],201);
                }
                
            }else{
                return response()->json(['message'=>'Failed','state'=>'change status'],400);
            }
            
            
                
            
            
//            
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        //
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        //
    }
}
