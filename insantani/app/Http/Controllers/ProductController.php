<?php

namespace App\Http\Controllers;
use App\ProductModel;
use App\NutritionFactsModel;
use Illuminate\Http\Request;
use App\Http\Requests;
use App\Http\Controllers\Controller;
//use Illuminate\Routing\Controller;


class ProductController extends Controller
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
    
    public function products(Request $request){
        
        
        
        $limit=$request->input('limit');
        $page=$request->input('page');
//        print_r($request->input());
//        echo(is_numeric($limit)==false);
//        echo($limit.$page);
        if($limit!=null && $page!=null&& count($request->input())==2
          && is_numeric($limit)==true && is_numeric($page)==true){

                $todos=ProductModel::paginate($limit);

            return [
                'message'=>'OK',
                'state'=>'list of all products',
                'result'=>$todos->toArray()
            ];
        }else{
            return response('Invalid Argument',400);
        }
    }

        
   
    
   
    

    
    public function productDetail($id){
        
        $segments = explode('/', $id);
        $todos=ProductModel::find($segments);
        

        
//        print_r($todos);
        
        if(count($todos)>0){
            foreach ($todos as $todo){
                $x=$todo->nutritionFacts;

            }
            return [
                'message'=>'OK',
                'state'=>'product detail',
                'result'=>$todos
            ];
        }else{
            return [
                'message'=>"NOT FOUND",
                'state'=>'product detail'
            ];
        }
        
    }
    
    
    
    public function relatedItems($id){
        
        $segments = explode('/', $id);
        $todos=ProductModel::where('farmer_username','=',$segments)->get();
        if(count($todos)>0){
            return [
                'message'=>'OK',
                'state'=>'related items',
                'result'=>$todos 
            ];
        }else{
            return [
                'message'=>'NOT FOUND',
                'state'=>'related items'
            ];
        }
        
    }
    
    public function showPicture($id){
        $segments = explode('/', $id);
        $todos=ProductModel::find($segments);
//        echo(public_path().$todos->pluck('product_filepath')[0]);
        if(count($todos)>0){
            $path=$todos->pluck('product_filepath');
            $filename=$todos->pluck('product_filename');
            ob_end_clean();
//          $todos['product_filename'];
            return  response()->download(public_path().$path[0], $filename[0], ['Content-Type'=>'image/png']);
        }else{
            return [
                'message'=>'NOT FOUND',
                'state'=>'products picture'
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
