import requests
import json


with open('API_Assessment.postman_collection.json') as json_file:

    #Load file content
    data = json.load(json_file)

    req_params=data["item"][0]["request"]
    req_url=req_params["url"]["raw"]

    print("Request params: ",req_params)

    #get all the required headers: 
    header={}
    for headerx in req_params["header"]:
        header[headerx["key"]]=headerx["value"]

    #get the body
    body={}
    bodyargs=data["item"][0]["request"]["body"]["urlencoded"]

    for arg in bodyargs:
        body[arg["key"]]=arg["value"] 

    #Post request to get data from the API 
    rslt=requests.post(req_url,data=body,headers=header)
    rslt=rslt.json()

    #get the authorization key 
    oauth_token=rslt["access_token"]
    product_request=data["item"][1]["request"]
    product_request_url=product_request["url"]["raw"]

    #Get header and insert Authorization key 
    header={}
    for headerx in product_request["header"]:
        key=headerx["key"]
        if key=="Authorization":
            header[headerx["key"]]="Bearer "+oauth_token
        else:
            header[headerx["key"]]=headerx["value"]

    
    
    # Already given key
    storeId=str(136085)

    #Testing Product API endpoint
    print("\n\t  Product API endpoint.....\n")
    products=requests.get("https://pfllink-api-generic-staging.azurewebsites.net/api/v1/store/"+storeId+"/product/",headers=header)
    all_products=products.json()
    print("\n\t Success stored all data in all_products....")

    #Storing all products id 
    productIds=[]
    for product in all_products:
        productIds.append(product["ID"])

    print("\n\t Total products:  ",len(productIds))

    #Testing individual product api end points
    print("\n\t Getting individual products of store \n")
    product_api_endpoint="https://pfllink-api-generic-staging.azurewebsites.net/api/v1/store/"+storeId+"/product/"

    for id in productIds:
        print("\t Getting details of product ID ",id, "\n")
        detail=requests.get(product_api_endpoint+str(id),headers=header)
        print(detail.json())
        print("\n===============\n")

print("Done testing API ENDPOINTS")



