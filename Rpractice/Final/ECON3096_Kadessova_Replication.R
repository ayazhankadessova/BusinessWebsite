data <- read.delim("/Users/ayazhan/Desktop/A1_data.txt", sep=",")
View(data)
#Table 3
#panel A
#replication fact accuracy index regression
index_fact_accuracy <- data$iraqdead + data$libby + data$miers
replication_1 <- lm(index_fact_accuracy ~ data$post + data$times + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator)
summary(replication_1)
#replication specific issue index regression
index_specific_issue <- data$mostimp_scandals + data$iraq_post + data$iraq + data$leak + data$alito
replication_2 <- lm(index_specific_issue ~ data$post + data$times + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator)
summary(replication_2)
#replication  broad policy index regression
index_broad_policy <- data$repfavorable + data$demunfavorable + data$bushapproval + data$conservative
replication_3 <- lm(index_broad_policy ~ data$post + data$times + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator)
summary(replication_3)

#panel B
#replication fact accuracy index regression
replication_4 <- lm(index_fact_accuracy ~ data$paper + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator)
summary(replication_4)
#replication specific issue index regression
replication_5 <- lm(index_specific_issue ~ data$paper + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator)
summary(replication_5)
#replication  broad policy index regression
replication_6 <- lm(index_broad_policy ~ data$paper + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator)
summary(replication_6)


#Table 4
#Panel A
#voted in 2005 election A
replication_7 <- lm(data$voted ~ data$post + data$times + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator)
summary(replication_7)
#voted in 2005 election B
replication_8 <- lm(data$voted2005g ~ data$post + data$times + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator)
summary(replication_8)
#voted in 2006 election B
replication_9 <- lm(data$voted2006g ~ data$post + data$times + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator)
summary(replication_9)
#voted for Democrats set to missing
replication_10 <- lm(data$voteddem ~ data$post + data$times + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator)
summary(replication_10)
#voted for Democrats set to zero
replication_11 <- lm(data$voteddem_all ~ data$post + data$times + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator)
summary(replication_11)

#panel B
#voted in 2005 election A
replication_12 <- lm(data$voted ~ data$paper + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator)
summary(replication_12)
#voted in 2005 election B
replication_13 <- lm(data$voted2005g ~ data$paper + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator)
summary(replication_13)
#voted in 2006 election B
replication_14 <- lm(data$voted2006g ~ data$paper + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator)
summary(replication_14)
#voted for Democrats set to missing
replication_15 <- lm(data$voteddem ~ data$paper + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator)
summary(replication_15)
#voted for Democrats set to zero
replication_16 <- lm(data$voteddem_all ~ data$paper + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator)
summary(replication_16)

install.packages("stargazer")
library(stargazer)
#table: Table 3, Panel A
stargazer(replication_1, replication_2, replication_3, type = "text", keep.stat = c("n","rsq"))
#table: Table 3, Panel B
stargazer(replication_4, replication_5, replication_6, type = "text", keep.stat = c("n","rsq"))
#table: Table 4, Panel A
stargazer(replication_7, replication_8, replication_9, replication_10, replication_11, type = "text", keep.stat = c("n","rsq"))
#table: Table 4, Panel B
stargazer(replication_12, replication_13, replication_14, replication_15, replication_16, type = "text", keep.stat = c("n","rsq"))


#Q3
#voted for Democrats set to missing, interaction term to see the effect of gender
replication_17 <- lm(data$voteddem ~ data$post*data$Bfemale + data$times + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator)
summary(replication_17)
stargazer(replication_17, type="text", keep.stat = c("n","rsq"))


#Q4
#voted in 2005 election A
reportedage_sq <- data$reportedage^2
replication_18 <- lm(data$voted ~ data$post + data$times + data$Bfemale + data$reportedage + reportedage_sq + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator)
summary(replication_18)
stargazer(replication_18, type="text", keep.stat = c("n","rsq"))


#Q5
#Table 4
#Panel A
#voted in 2005 election A
replication_19 <- glm(data$voted ~ data$post + data$times + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator, binomial)
summary(replication_19)
#voted in 2005 election B
replication_20 <- glm(data$voted2005g ~ data$post + data$times + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator, binomial)
summary(replication_20)
#voted in 2006 election B
replication_21 <- glm(data$voted2006g ~ data$post + data$times + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator, binomial)
summary(replication_21)
#voted for Democrats set to missing
replication_22 <- glm(data$voteddem ~ data$post + data$times + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator, binomial)
summary(replication_22)
#voted for Democrats set to zero
replication_23 <- glm(data$voteddem_all ~ data$post + data$times + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator, binomial)
summary(replication_23)

#panel B
#voted in 2005 election A
replication_24 <- glm(data$voted ~ data$paper + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator, binomial)
summary(replication_24)
#voted in 2005 election B
replication_25 <- glm(data$voted2005g ~ data$paper + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator, binomial)
summary(replication_25)
#voted in 2006 election B
replication_26 <- glm(data$voted2006g ~ data$paper + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator, binomial)
summary(replication_26)
#voted for Democrats set to missing
replication_27 <- glm(data$voteddem ~ data$paper + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator, binomial)
summary(replication_27)
#voted for Democrats set to zero
replication_28 <- glm(data$voteddem_all ~ data$paper + data$Bfemale + data$reportedage + data$Bvoted2001 + data$Bvoted2002 + data$Bvoted2004 + data$Bconsumer + data$Bgetsmag + data$Bpreferdem + data$wave2 + data$cells + data$dateoperator, binomial)
summary(replication_28)

#table: Table 4, Panel A
stargazer(replication_19, replication_20, replication_21, replication_22, replication_23, type = "text", keep.stat = c("n","rsq"))
#table: Table 4, Panel B
stargazer(replication_24, replication_25, replication_26, replication_27, replication_28, type = "text", keep.stat = c("n","rsq"))
