import re
import csv
from typing import final

### Group project with Yerassyl Mabiyev.

"""
EXECUTE TO RUN IT. There is a function call in the end if you wish to look at it.


Sometimes, if a person has commited an offensive foul, the sample data from exercise description will not match the data shown in the table.

For example:
Sample from exercise description:
Name    PF TOV
K.Durant 3 4 

Our table:
Name    PF TOV
K.Durant 4 5

This is because this exercise does not count Offensive fouls as Turnovers and PFs therefore, the amount of PFs and TOVs will always be higher than that of the sample. 

This is why we count Offensive fouls as Turnovers:

\"Any offensive violation by a player will result in a turnover being charged e.g. 3 second violation, offensive foul, offensive goal tending, double dribble violation etc 
\"


Also, the total number of points for the Away team is two points lower. It's because Stephen Curry is counted five free throws. while in the files there are only 3 instances of Stephen Curry making free throws at all.
Apart from that, there are absolutely no other discrepancies... 
"""

###Finding all the players
def find_all_players(play_by_play):

    name_pattern = re.compile(r'\w\. \w+', re.I) # re.I = ignore the case
    
    all_players = []

    for event in play_by_play:
        name = name_pattern.search(event[-1]) #search in the desciption
        if name:
            all_players.append(name.group(0)) #name is an object, matched thing is 0

    return list(set(all_players)) #only unique name, but make it list again bc

def all_player_stats(play_by_play, all_players):
    ###These are the patterns we should use while iterating through description - finding stuff.
    turnover_pattern = re.compile(r'Turnover by (\w\. \w+)', re.I)

    point2makes_pattern = re.compile(r'(\w\. \w+) makes 2-pt', re.I) #dont care lower or uppercase, layup included
    point2misses_pattern = re.compile(r'(\w\. \w+) misses 2-pt', re.I)

# attempts = makes + misses

    point3makes_pattern = re.compile(r'(\w\. \w+) makes 3-pt', re.I) 
    point3misses_pattern = re.compile(r'(\w\. \w+) misses 3-pt', re.I)

    offReb_pattern = re.compile(r'Offensive rebound by (\w\. \w+)', re.I)
    defReb_pattern = re.compile(r'Defensive rebound by (\w\. \w+)', re.I)

    freethrowmakes_pattern = re.compile(r'(\w\. \w+) makes free throw', re.I)
    freethrowmisses_pattern = re.compile(r'(\w\. \w+) misses free throw', re.I)

    personalFoul_pattern = re.compile(r'foul by (\w\. \w+)', re.I) #dont need drawn by 
    looseFoul_pattern = re.compile(r'Loose ball foul by (\w\. \w+)', re.I)
    assist_pattern = re.compile(r'assist by (\w\. \w+)', re.I)
    offensiveFoul_pattern = re.compile(r'Offensive foul by (\w\. \w+)', re.I)

    block_pattern = re.compile(r'block by (\w\. \w+)', re.I)

    steal_pattern = re.compile(r'steal by (\w\. \w+)', re.I)

    players_stats = []

    for player in all_players:

        player_data = {"player_name": 0, "FG": 0, "FGA": 0, "FG%": 0, "3P": 0, "3PA": 0, "3P%": 0, "FT": 0, "FTA": 0, "FT%": 0, "ORB": 0, "DRB": 0, "TRB": 0, "AST": 0, "STL": 0, "BLK": 0, "TOV": 0, "PF": 0, "PTS": 0}
        player_data['player_name'] = player

        for event in play_by_play: # event is column, we need the last column for each play

            # MADE 3 pts

            point3 = point3makes_pattern.search(event[-1])
            if point3:
                if point3.group(1) == player: # 0 = whole match, 1 = in brackets
                    player_data ['3P'] += 1 # add to 3 points made
                    player_data ['3PA'] += 1 # add to 3 points attempted
                    player_data ['FG'] += 1  # add to Field Goal
                    player_data ['FGA'] += 1 # add to Field Goal Attempts
                    player_data ['PTS'] += 3

            # MISSED 3 pts

            point3attempts = point3misses_pattern.search(event[-1])

            if point3attempts:
                if point3attempts.group(1) == player:
                    player_data ['3PA'] += 1 # add to 3 points attempted 
                    player_data ['FGA'] += 1 

            # MADE 2 pts

            point2 = point2makes_pattern.search(event[-1])
            if point2:
                if point2.group(1) == player: # 0 = whole match, 1 = in brackets
                    player_data ['FG'] += 1 # add to Field Goal
                    player_data ['FGA'] += 1   
                    player_data ['PTS'] += 2

            # MISSED 2 pts
            
            point2attempts = point2misses_pattern.search(event[-1])

            if point2attempts:
                if point2attempts.group(1) == player: 
                    player_data ['FGA'] += 1 

            # MADE FREE THROW
            
            freethrow = freethrowmakes_pattern.search(event[-1])

            if freethrow:
                if freethrow.group(1) == player:
                    player_data ['FT'] += 1 
                    player_data ['FTA'] += 1 
                    player_data ['PTS'] += 1  

            # MISSED FREE THROW

            freethrowattempts = freethrowmisses_pattern.search(event[-1])

            if freethrowattempts:
                if freethrowattempts.group(1) == player:
                    player_data ['FTA'] += 1 
                
            # ORB

            offReb = offReb_pattern.search(event[-1])

            if offReb:
                if offReb.group(1) == player:
                    player_data ['ORB'] += 1 
                    player_data ['TRB'] += 1 

            # DRB

            defReb = defReb_pattern.search(event[-1])

            if defReb:
                if defReb.group(1) == player:
                    player_data ['DRB'] += 1 
                    player_data ['TRB'] += 1

            # ASSISTS

            assist = assist_pattern.search(event[-1])

            if assist:
                if assist.group(1) == player:
                    player_data ['AST'] += 1 

            # STEALS

            steal = steal_pattern.search(event[-1])

            if steal:
                if steal.group(1) == player:
                    player_data ['STL'] += 1 
                    

            # BLOCK

            block = block_pattern.search(event[-1])

            if block:
                if block.group(1) == player:
                    player_data ['BLK'] += 1 # add to 3 points attempted 
            
            # TURNOVER
            
            turnover = turnover_pattern.search(event[-1])

            if turnover:
                if turnover.group(1) == player:
                    player_data ['TOV'] += 1

                        


            # PERSONAL FOUL

            personalFoul = personalFoul_pattern.search(event[-1])

            if personalFoul:
                if personalFoul.group(1) == player:
                    player_data ['PF'] += 1 
                    

            
            looseFoul = looseFoul_pattern.search(event[-1])
            
            if looseFoul:
                if(looseFoul.group(1)) == player:
                    player_data ['PF'] += 1
            
            offFoul = offensiveFoul_pattern.search(event[-1])
            
            if offFoul:
                if(offFoul.group(1))==player:

                    player_data['PF']+=1
                    player_data['TOV']+=1
                    
            if(player_data['FTA']!=0):
                player_data ['FT%'] = round(player_data['FT'] / player_data['FTA'], 3)
            if(player_data['3PA']!=0):
                player_data ['3P%'] = round(player_data['3P'] / player_data['3PA'], 3)
            if(player_data['FGA']!=0):
                player_data['FG%'] = round(player_data['FG']/player_data['FGA'], 3)

                    
        players_stats.append(player_data)

    return players_stats
teams = []
def match_to_team(play_by_play, all_players):

    team_player = {name: '' for name in all_players}
    
    point2makes_pattern = re.compile(r'(\w\. \w+)makes 2-pt', re.I)
    point2misses_pattern = re.compile(r'(\w\. \w+) misses 2-pt', re.I)
    personalFoul_pattern = re.compile(r'foul by (\w\. \w+)', re.I)                                                                                                                                                                  
    defReb_pattern = re.compile(r'Defensive rebound by (\w\. \w+)', re.I)
    for event in play_by_play:
        point2 = point2makes_pattern.search(event[-1])
        point2attempts = point2misses_pattern.search(event[-1])
        personalFoul = personalFoul_pattern.search(event[-1])
        rebound = defReb_pattern.search(event[-1])
        if rebound:
           name = rebound.group(1)
           team_player[name] = event[2]
           
        if point2:
            name = point2.group(1)
            team_player[name] = event[2] # event[2] -> relevant team
            

        if point2attempts:
            name = point2attempts.group(1)
            team_player[name] = event[2] # event[2] -> relevant team


 
    return team_player


def analyse_nba_game():
    
    with open('NbaGame1.txt') as csv_text:
        text_line = csv.reader(csv_text, delimiter='|')
        play_by_play = [each for each in text_line]
        teams.append(play_by_play[0][3])
        teams.append(play_by_play[0][4])
        
    
    all_players =  find_all_players(play_by_play)
    players_stats = all_player_stats(play_by_play, all_players)
    team_player=match_to_team(play_by_play, all_players)
    final_dict = {}
    
    
    ##Assigning to teams and returning as a dictionary:

    data_home = []
    data_away = []
    
    for i in players_stats:
        for key in team_player.keys():
            if i['player_name']==key:
                if team_player[key]==teams[0]:
                    data_home.append(i)
                else:
                    data_away.append(i)
    ##Home Team totals
    
    totals = {"player_name": "", "FG":0, "FGA":0, "FG%":0,   "3P":0,  "3PA":0,  "3P%":0,  "FT":0,  "FTA":0,  "FT%":0, "ORB":0,  "DRB":0, "TRB":0, "AST":0 , "STL":0,  "BLK":0,  "TOV":0, "PF":0, "PTS":0}
    totals["player_name"] = "Team Totals"
    
    for player in data_home:
        totals["FG"] += player["FG"]
        totals["FGA"] += player["FGA"]
        totals["3P"] += player["3P"]
        totals["FT"] += player["FT"]
        totals["FTA"] += player["FTA"]
        totals["3PA"] += player["3PA"]
        totals["ORB"] += player["ORB"]
        totals["DRB"] += player["DRB"]
        totals["TRB"] += player["TRB"]
        totals["AST"] += player["AST"]
        totals["STL"] += player["STL"]
        totals["BLK"] += player["BLK"]
        totals["TOV"] += player["TOV"]
        totals["PF"] += player["PF"]
        totals["PTS"] += player["PTS"]
    
    if(totals['FGA']!=0):
        totals['FG%'] = round(totals['FG']/totals['FGA'], 3)
    if(totals['3PA']!=0):
        totals['3P%'] = round(totals['3P']/totals['3PA'], 3)
    if(totals['FTA']!=0):
        totals['FT%'] = round(totals['FT']/totals['FTA'], 3)
    data_home.append(totals)
    final_dict["home_team"] = {"name": teams[1], "players_data":data_home}
    ###Away team totals
    total = {"player_name": "", "FG":0, "FGA":0, "FG%":0,   "3P":0,  "3PA":0,  "3P%":0,  "FT":0,  "FTA":0,  "FT%":0, "ORB":0,  "DRB":0, "TRB":0, "AST":0 , "STL":0,  "BLK":0,  "TOV":0, "PF":0, "PTS":0}
    total["player_name"] = "Team Totals"
    for player in data_away:
        total["FG"] += player["FG"]
        total["FGA"] += player["FGA"]
        total["3P"] += player["3P"]
        total["3PA"] += player["3PA"]
        total["ORB"] += player["ORB"]
        total["FT"] += player["FT"]
        total["FTA"] += player["FTA"]
        total["DRB"] += player["DRB"]
        total["TRB"] += player["TRB"]
        total["AST"] += player["AST"]
        total["STL"] += player["STL"]
        total["BLK"] += player["BLK"]
        total["TOV"] += player["TOV"]
        total["PF"] += player["PF"]
        total["PTS"] += player["PTS"]
    ###Percentages
    if(total['FGA']!=0):
        total['FG%'] = round(total['FG']/total['FGA'], 3)
    if(total['3PA']!=0):
        total['3P%'] = round(total['3P']/total['3PA'], 3)
    if(total['FTA']!=0):
        total['FT%'] = round(total['FT']/total['FTA'], 3)
    
    data_away.append(total)
    final_dict["away_team"] = {"name": teams[0], "players_data":data_away}
 
    return final_dict

 #unpack, \t = 4 spaces
def print_nba_game_stats(team_dict):
    ###Printing stat names (PF, TOV, FG etc.) 
    print(*analyse_nba_game()["home_team"]["players_data"][0].keys(), sep = '\t')
    ##Printing home team
    for player in team_dict["home_team"]["players_data"]:
        print(*player.values(), sep = '\t')
    
    for player in team_dict["away_team"]["players_data"]:
        print(*player.values(), sep='\t')

###This is calling a function
print(print_nba_game_stats(analyse_nba_game()))

