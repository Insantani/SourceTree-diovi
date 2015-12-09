var should =require('chai').should(),
    expect=require('chai').expect,
    supertest=require('supertest'),
    api=supertest('http://localhost:3000')
//var assert = require('assert');
describe('Article', function() {
    it('should be an object with keys and values', function (){
        api.get('/feed/1')
        .set('Accept','application/json')
        .expect(200)
        .end(function(err,res){
            expect(res.body).to.have.property("article_id");
expect(res.body.article_id).to.not.equal(null);
expect(res.body).to.have.property("author");
expect(res.body.author).to.not.equal(null);
expect(res.body).to.have.property("article_title"); 
expect(res.body.article_title).to.not.equal(null);
expect(res.body).to.have.property("picture");
expect(res.body).to.have.property("content");
expect(res.body.content).to.not.equal(null);
done();
        });
     
    });
});
    
describe ('Shopping Cart',function(){
    var product_id;
    var farmer;
    var quantity;
    it('should be updated with a new quantity', function (){
        api.get('/shopping_cart/cart/1WQ46710DS0')
        .set('Accept','application/json')
        .expect(200)
        .end(function(err,res){
            product_id=res.body.product_id;
            farmer=res.body.farmer;
            quantity=res.body.quantity;
            });
        
        api.put('/shopping_cart/cart/1WQ46710DS0/edit/1')
        .set('Accept','application/json')
        .send({
            product_id: 1,
            farmer:"Izhar Almizan",
            quantity:3    
        })
        .expect(201)
        .end(function(err,res){
expect(res.body.product_id).to.equal(product_id);
expect(res.body.farmer).to.equal(farmer);
expect(res.body.quantity).to.equal(3);
done();
        });
     
    });
  });
