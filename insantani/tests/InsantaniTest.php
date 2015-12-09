<?php

use Illuminate\Foundation\Testing\WithoutMiddleware;
use Illuminate\Foundation\Testing\DatabaseMigrations;
use Illuminate\Foundation\Testing\DatabaseTransactions;

class InsantaniTest extends TestCase
{
    /**
     * A basic test example.
     *
     * @return void
     */
    public function test1()
    {
        $this->call('GET','api/feed?page=0&limit=1');
        $this->assertResponseStatus(200);
        
        
    }
    public function test2()
    {
        $this->call('GET','api/feed?page=0');
        $this->assertResponseStatus(400);
        
        
    }
    public function test3()
    {
        $this->call('GET','api/feed?limit=1');
        $this->assertResponseStatus(400);
        
        
    }
    public function test4()
    {
        $this->call('GET','api/feed');
        $this->assertResponseStatus(400);
        
        
    }
    public function test5()
    {
        $this->call('GET','api/products?page=0&limit=1');
        $this->assertResponseStatus(200);
        
        
    }
    public function test6()
    {
        $this->call('GET','api/products');
        $this->assertResponseStatus(400);
        
        
    }
    public function test7()
    {
        $this->call('GET','api/products?limit=0');
        $this->assertResponseStatus(400);
        
        
    }
    public function test8()
    {
        $this->call('GET','api/products?page=0');
        $this->assertResponseStatus(400);
        
        
    }
    
    public function test9()
    {
        $this->call('GET','api/products/1');
        $this->assertResponseStatus(200);
        
        
    }
    public function test10()
    {
        $this->call('GET','api/products/lalala');
        $this->assertResponseStatus(404);
        
        
    }
    public function test11()
    {
        $this->call('GET','api/products/lalala/90');
        $this->assertResponseStatus(404);
        
        
    }
    public function test12()
    {
        $this->call('GET','api/products?a=0');
        $this->assertResponseStatus(400);
        
        
    }
    public function test13()
    {
        $this->call('GET','api/feed?a=0');
        $this->assertResponseStatus(400);
        
        
    }
    public function test14()
    {
        $this->call('GET','api/search/product/0');
        $this->assertResponseStatus(200);
        
        
    }
    public function test15()
    {
        $this->call('GET','api/search/product/a');
        $this->assertResponseStatus(200);
        
        
    }
    public function test16()
    {
        $this->call('GET','api/search/tag/a');
        $this->assertResponseStatus(200);
        
        
    }
    public function test17()
    {
        $this->call('GET','api/search/tag');
        $this->assertResponseStatus(404);
        
        
    }
    public function test18()
    {
        $this->call('GET','api/search/product');
        $this->assertResponseStatus(404);
        
        
    }
    public function test19()
    {
        $this->call('GET','api/search/tag/0');
        $this->assertResponseStatus(200);
        
        
    }
    public function test20()
    {
        $this->call('GET','api/farmer/stevejobs/products');
        $this->assertResponseStatus(200); 
    }
    public function test21()
    {
        $this->call('GET','api/feed/article/1');
        $this->assertResponseStatus(200); 
    }
    public function test22()
    {
        $this->call('GET','api/feed/article/aaa');
        $this->assertResponseStatus(404); 
    }
    public function test23()
    {
        $this->call('GET','api/tag/carrot/results');
        $this->assertResponseStatus(200); 
    }
    public function test24()
    {
        $this->call('GET','api/tag/a/results');
        $this->assertResponseStatus(404); 
    }
    public function test25()
    {
        $this->call('GET','api/tag/results');
        $this->assertResponseStatus(404); 
    }
    public function test26()
    {
        $this->call('POST','api/register',["name"=>"Yohana Kanisia","password"=>"password","email"=>"yohana.kanisia@gmail.com","phone_number"=>"08129530405","address"=>"cibubur","password_confirmation"=>"password"]);
        $this->assertResponseStatus(201);
    }
    public function test27()
    {
        $this->call('POST','api/register',["name"=>"Yohana Kanisia","password"=>"password","phone_number"=>"08129530405","address"=>"cibubur","password_confirmation"=>"password"]);
        $this->assertResponseStatus(200);
    }
    public function test28()
    {
        $this->call('POST','api/register',["name"=>"Yohana Kanisia","password"=>"password","email"=>"yohana.kanisia@gmail.com","phone_number"=>"08129530405","address"=>"cibubur","password_confirmation"=>"jo"]);
        $this->assertResponseStatus(200);
    }
    public function test29()
    {
        $this->call('POST','api/register',["name"=>"Yohana Kanisia","password"=>"password","email"=>"yohana.kanisia@gmail.com","phone_number"=>"08129530405","address"=>"cibubur","password_confirmation"=>"password"]);
        $this->assertResponseStatus(500);
    }
}
