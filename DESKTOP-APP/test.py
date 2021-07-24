# importing the requests library
import requests

# api-endpoint
URL = "https://606e83ef0c054f0017657193.mockapi.io/employee"

# location given here
location = "delhi technological university"

# defining a params dict for the parameters to be sent to the API
PARAMS = {}

# sending get request and saving the response as response object
r = requests.get(url=URL, params=PARAMS)

# extracting data in json format
data = r.json()
for employee in data:
    print(employee)

