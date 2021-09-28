
var query = 'mac:001F3C277E90';
var globalQuery = '001F3C277E90';
var queryUpperCase = 'MAC:001F3C277E90';
var queryTwoFields = 'mac:001F3C277E90 subscriber:1234';
var queryWithBlank = 'mac: 001F3C277E90  subscriber :1234';
var subscriber = '1234';
var queryByProduct = "product:Full 10 Megas";

test( "Getting the query builder", function() {
  var qBuilder = new SearchQueryBuilder(query);
  equal( query ,  qBuilder.getQuery() , "getQuery");
});

test("Getting the global search",function (){
	var qBuilder = new SearchQueryBuilder(globalQuery);

	equal( globalQuery , qBuilder.getTablaGlobalSearchObject()["sSearch"], "getQuerySearchObject" );
});

test("Getting the object query with MAC",function (){
	var qBuilder = new SearchQueryBuilder(query);		

	equal("sSearch_0",qBuilder.getQuerySearchObject()["mac"]["key"],"getQuerySearchObject MAC");
	equal(globalQuery,qBuilder.getQuerySearchObject()["mac"]["value"],"getQuerySearchObject MAC");
});


test("Global search with two element",function (){
	var qBuilder = new SearchQueryBuilder(queryTwoFields);
	equal("sSearch_0",qBuilder.getQuerySearchObject()["mac"]["key"],"getQuerySearchObject MAC field name");
	equal(globalQuery,qBuilder.getQuerySearchObject()["mac"]["value"],"getQuerySearchObject MAC value");

	equal("sSearch_2",qBuilder.getQuerySearchObject()["subscriber"]["key"],"getQuerySearchObject Subcriptor field name");
	equal(subscriber,qBuilder.getQuerySearchObject()["subscriber"]["value"],"getQuerySearchObject Subcriptor value");
	

});


test("Getting the query in MAC that has uppercase",function (){
	var qBuilder = new SearchQueryBuilder(queryUpperCase);

	equal("sSearch_0",qBuilder.getQuerySearchObject()["mac"]["key"],"getQuerySearchObject MAC");
	equal(globalQuery,qBuilder.getQuerySearchObject()["mac"]["value"],"getQuerySearchObject MAC");	
});


test("Two field with blank",function (){
	var qBuilder = new SearchQueryBuilder(queryTwoFields);
	equal("sSearch_0",qBuilder.getQuerySearchObject()["mac"]["key"],"getQuerySearchObject MAC field name");
	equal(globalQuery,qBuilder.getQuerySearchObject()["mac"]["value"],"getQuerySearchObject MAC value");

	equal("sSearch_2",qBuilder.getQuerySearchObject()["subscriber"]["key"],"getQuerySearchObject Subcriptor field name");
	equal(subscriber,qBuilder.getQuerySearchObject()["subscriber"]["value"],"getQuerySearchObject Subcriptor value");
	

});

test("Getting the object that uses the table",function (){
		var qBuilder = new SearchQueryBuilder(queryTwoFields);

		equal("001F3C277E90",qBuilder.getTableSearchObject()[0]["sSearch"],"getQuerySearchObject MAC field name");
		equal("1234",qBuilder.getTableSearchObject()[2]["sSearch"],"getQuerySearchObject MAC field name");
	
});

test( "Empty query", function() {
  	var qBuilder = new SearchQueryBuilder("");
  	equal("sSearch_0",qBuilder.getQuerySearchObject()["mac"]["key"],"getQuerySearchObject MAC field name");
	equal("",qBuilder.getQuerySearchObject()["mac"]["value"],"getQuerySearchObject MAC value");
});


test("Find by Product Name",function (){
	var qBuilder = new SearchQueryBuilder(queryByProduct);
	equal("Full 10 Megas",qBuilder.getTableSearchObject()[3]["sSearch"],"getQuerySearchObject Product field name");
	
});


/*test("",function (){

	
});


test("",function (){

	
});

test("",function (){

	
});


test("",function (){

	
});


test("",function (){

	
});


*/
