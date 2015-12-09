<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Requests;
use App\ProductModel;
use App\TagsModel;
use App\Http\Controllers\Controller;

class SearchController extends Controller
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
    
    public function searchProduct($query){
        
        $segments = explode('/', $query);
        $todos=ProductModel::where('product_name','like','%'.$segments[0].'%')->get();
        if(count($todos)>0){
            return [
                'message'=>'FOUND',
                'state'=>'search results',
                'result'=>$todos 
            ];
        }else{
             return [
                'message'=>'NOT FOUND',
                'state'=>'search results'
            ];
        }
        
        
    }
    
    public function searchTags($query){
        
        $segments = explode('/', $query);
        $todos=TagsModel::where('tags','like','%'.$segments[0].'%')->get();
        
        $x2=array();
        foreach ($todos as $todo){
            $x=$todo->tagArticles;
            
//            foreach ($x as $x1){
//                $x1->article;
//                array_push($x2,$x);
//            }
            
        }
        if (count($todos)>0){
            return [
                'message'=>'FOUND',
                'state'=>'search results',
                'result'=>$todos
            ];
    } else{
            return [
                'message'=>'NOT FOUND',
                'state'=>'search results'
            ];
        }
        
        
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
