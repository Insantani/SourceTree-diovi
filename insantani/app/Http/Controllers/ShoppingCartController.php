<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;
use App\ShoppingCartModel;

use App\Http\Controllers\Controller;

class ShoppingCartController extends Controller
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
        
        $data=$request->all();
        $checkItems=ShoppingCartModel::where('user_id','=',$data['user_id'])
                                     ->where('product_id','=',$data['product_id'])
                                     ->get();
        if(count($checkItems)==0){
            $todo=ShoppingCartModel::create([
                "product_id"=>$data['product_id'],
                "product_quantity"=>$data['product_quantity'],
                "user_id"=>$data['user_id']
            ]);
            $todo->save();
            return response()->json(['message'=>'OK'],201);
            
        }else{
            return response()->json([
                'message'=>"You have a similar item in your cart",
                'state'=>'add item to cart'
                ],400);
        }
        
        
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show(Request $request)
    {
        //
//        $segments=explode('/',$id);
        $user_id=$request->input('user_id');
        $todo=ShoppingCartModel::where('user_id','=',$user_id)->get();
//        if (count($todo)>0){
            return [
                "message"=>"OK",
                "state"=>"Shopping Cart Items",
                "data"=>$todo
            ];
//        }else{
//            return response("Not Found",404);
        
//        }
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
    public function update(Request $request)
    {
        //$id means that is for user_id
//        $segments=explode('/',$id);
        $data=$request->all();
        $todos=ShoppingCartModel::where("user_id","=",$data['user_id'])
                                ->where("product_id","=",$data['product_id'])
                                ->update(['product_quantity'=>$data['product_quantity']]);
        
//        $todos->product_quantity=$data['product_quantity'];
//        $todos->save();
        return response()->json(["message"=>"Success"],201);
        
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy(Request $request)
    {
        //
        $data=$request->all();
//        print_r($data);
        $todos=ShoppingCartModel::where("user_id","=",$data['user_id'])
                                ->where("product_id","=",$data['product_id']);
        $todos->delete();
        return response()->json(["message"=>"Success"],201);
        
    }
}
