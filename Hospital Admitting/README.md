**Two Inputs:**

1. Patients dataset - Sheet1.csv

Contains information (ID_Card,Name,Age,Gender,Symptoms) about the patients.

2. new_Diseases and Symptoms - Sheet1.csv

Contains 6 Lung Diseases and Symptoms of these diseases.

Program Flow:

1. Shuffle the 'Patients dataset - Sheet1.csv' to create the 'PatientsEnterHospital.csv'.
2. Add pateients to the hospital based on the following critera:

- Does the admittied patients number exceed the capacity?
- Can the hospital assign two symptoms of the patient to any of the diseases that it treats (get from new_Diseases and Symptoms - Sheet1.csv).

3. Create a csv for Admitted/not Admitted patients.

Output: 'Hospital_admitted.csv', 'Hospital_NOTadmitted.csv'.

4. Recommend Different Lung Diseases treatment hospitals to not admitted patients.

- Scrape https://www21.ha.org.hk/smartpatient/SPW/en-us/Useful-Resource/Patient-Group/List/?guid=ebe3f3f7-cd2b-4573-882e-cfd61eac55ac with BeautifulSoup -> make a Dataframe -> create a csv of recommended hospitals.

5. Visualizing the frequency of diseases of Admitted Patients with matplotlib. 

**Every time the code is run, the output 'Hospital_admitted.csv', 'Hospital_NOTadmitted.csv' will be different because the 'Patients dataset - Sheet1.csv' will always be shuffled differentely.**

Hence, the bar plot will be different for every run.

Suggestion for next time: Change the writing_csv function's 1s checking condition to hospital.capacity, so it can be used for hosptals with different capacities.
