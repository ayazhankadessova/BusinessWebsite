import re

from requests_html import HTMLSession
from bs4 import BeautifulSoup

s = HTMLSession()

url_initial = 'https://fa-fa.kz/search_load/gruzy-almaty/'

def getsoup(url):
    r = s.get(url)
    # r.html.render(sleep=1)
    soup_initial = BeautifulSoup(r.html.html, 'html.parser')
    return soup_initial

def transform(soup):

    extracted_data = []
    i = 0
    while i<10:
        destination = soup.find_all('a', {'class': "o_dest"})[i].text
        
        product_name = soup.find_all('td', {'style': "width:8%;max-width:80px; word-wrap:break-word;padding-right:5px"})[i].text

        pattern = r'[0-9]'
        new_product_name = re.sub(pattern, '', product_name).replace('-т\xa0/\xa0-м', '').replace('т\xa0/\xa0м', '')

        price = soup.find_all('td', {'style': "width:40%;max-width:400px"})[i].text.split(' ')[-3:]

        new_price = ''
        for _ in price:
            new_price += str(_) + ' '

        if 'тнг' not in new_price:
            new_price = 0

        Special_req = soup.find_all('td', {'style': "width:40%;max-width:400px"})[i].text.split(' ')[-6:-3]

        new_Special_req = ''
        for _ in Special_req:
            new_Special_req += str(_) + ' '
        
        extracted_data.append(
            {
                'Index' : i,
               'From, To': str(destination),
            #    'To': str(destination[3]) + ' ' + str(destination[4]) ,
               'Product Name': str(new_product_name),
               'Price': str(new_price).replace('нал', "").replace('б/', ""),
               'Special Request': re.sub(pattern, '', new_Special_req).replace('км', '').strip().replace('KZ', '').replace('км', '')
            }
        )
        i+=1

    return extracted_data


def format(repositories_data):
    result = []

    for d in repositories_data:
        collect_list = []
        for v in d.values():
            collect_list.append(v)
        result.append(collect_list)

    # header = ['From', 'To', 'Product Name', 'Price', 'Special Request']
    # result.insert(0, header)

    str_value = []
    for i in result:
        str_value.extend(', '.join(i).split('\n'))
    final = '\n'.join(str_value)
    
    with open('fafa_Almaty.csv', 'a') as csvfile:
                csvfile.write(final)
                csvfile.write('\n')

def getnextpage(soup):
    pages_number = int(soup.find('span', {'class': "pages"}).text.split(' ')[3])
    i = 1
    while i < pages_number:
        try:
            url = 'https://fa-fa.kz/search_load/gruzy-almaty/' + str(i) + '/'
            print(url)
            html_repos = getsoup(url)
            rep_d = transform(html_repos)
            format(rep_d)
            print('page ' + str(i) + ' finished!')
            i+=1
        except IndexError:
            break
        # return url
    
# url = 'https://fa-fa.kz/search_load/gruzy-almaty/19/'
# html_repos = getsoup(url)
# rep_d = transform(html_repos)
# format(rep_d)
soup_initial = getsoup(url_initial)
getnextpage(soup_initial)
