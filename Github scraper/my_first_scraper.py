import requests
import bs4 as bs
def request_github_trending(url):
    r = requests.get(url).text
    return r


def extract(page):
    soup = bs.BeautifulSoup(request_github_trending('https://github.com/trending'), 'lxml')
    concat = ""
    i=0
    ##Find all instances of row, make it all a string(concat) to manipulate easier. 
    while i < 25:
        ##Get all text from rows and add it to concat
        for link in soup.find_all("article"):
            concat+=(link.text.replace(' ', "").replace("\n", " ").replace("Star", "").replace("\t", ""))
            i+=1

    return concat


    
def transform(html_repos):
    
    res = []
    arr = html_repos.split(" ")
    ##Getting rid of unnesessary spaces from array.
    
    arr = list(filter(None, arr))
    arr = list(filter(bool, arr))
    arr = list(filter(len, arr))
    arr = list(filter(lambda item: item, arr))
    dict = {}
    ##Filtering information that we need.
    
    for i in range(0, len(arr)):
        if "/" in arr[i]:
            arr[i]=arr[i].replace("/", "")
            if len(arr[i])<20:
                res.append(arr[i])
                res.append(arr[i+1])
        elif "starstoday" in arr[i]:
            arr[i]=arr[i].strip("starstoday")
            res.append(arr[i])
            
    ##Putting everything into key:value pairs
    return [{'developer': n, 'repository_name': r, 'nbr_stars': s} for n, r, s in [res[i: i + 3] for i in range(0, len(res), 3)]]
         
def format(repositories_data):
    concat=""
    concat+="Developer,Repository Name,Number of Stars\n"
    arr = repositories_data
    for i in range(0, len(arr)):
        concat+=','.join(arr[i].values()) +'\n'
        
    return concat

print(format(transform(extract(""))))