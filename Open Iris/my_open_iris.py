import warnings
import pandas as pd
import numpy as np
import seaborn as sns


def warn(*args, **kwargs):
    pass
import warnings
warnings.warn = warn


def load_dataset():

    iris = pd.read_csv('iris.csv')
    return iris

def summarize_dataset():
    print('Shape of the Dataset is: ')
    print(iris.shape) 
    print('----------------------------') 
    print('First 10 lines of the Dataset are: ')
    print(iris.head(10))
    print('----------------------------') 
    print('Statistical summary of the Dataset: ')
    print(iris.describe())
    print('----------------------------') 
    print('Distribution of the Dataset: ')
    print(iris.groupby('class').count())
    print('----------------------------') 

def print_plot_univariate():
    print('Univariate plot is, figure 1: ')
    sns.distplot(iris['sepal-length'], kde = False)

def print_plot_multivariate():
    print('Multivariate plot is, figure 2: ')
    sns.pairplot(iris, hue = 'class')

from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.datasets import make_moons, make_circles, make_classification
from sklearn.neural_network import MLPClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.svm import SVC
from sklearn import svm
from sklearn.gaussian_process import GaussianProcessClassifier
from sklearn.gaussian_process.kernels import RBF
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import RandomForestClassifier, AdaBoostClassifier
from sklearn.naive_bayes import GaussianNB
from sklearn.discriminant_analysis import LinearDiscriminantAnalysis
from sklearn.linear_model import LogisticRegression
from sklearn.neighbors import KNeighborsClassifier

from sklearn.model_selection import cross_val_score
from sklearn.model_selection import train_test_split

from sklearn.metrics import classification_report, confusion_matrix, accuracy_score

def my_print_and_test_models():
    
    x = iris.drop('class', axis = 1) #everything except class

    y = iris['class']

    x_train, x_test, y_train, y_test = train_test_split(x, y, test_size = 1/3, random_state = 1) #0.333 test size and randomly

    dt_model = DecisionTreeClassifier()
    dt_model.fit(x_train, y_train) #borders for 70%
    dt_predictions = dt_model.predict(x_test)
    
    print('%s: %f (%f)' % (dt_model, accuracy_score(y_test, dt_predictions), calc_std(dt_model)))

    dt_model3 = KNeighborsClassifier()
    dt_model3.fit(x_train, y_train) #borders for 70%
    dt_predictions3 = dt_model3.predict(x_test)

    print('%s: %f (%f)' % (dt_model3, accuracy_score(y_test, dt_predictions3), calc_std(dt_model3)))
    
    dt_model4 = LogisticRegression()
    dt_model4.fit(x_train, y_train) #borders for 70%
    dt_predictions4 = dt_model4.predict(x_test)
    print('%s: %f (%f)' % (dt_model4, accuracy_score(y_test, dt_predictions4), calc_std(dt_model4)))
    
    

    svm_model = SVC()
    svm_model.fit(x_train, y_train)
    dt_predictions8 = svm_model.predict(x_test)
    print('%s: %f (%f)' % (svm_model, accuracy_score(y_test, dt_predictions8), calc_std(svm_model)))
    
    
def calc_std(model):
    arr_acc = []
    for i in range(10):
        x = iris.drop('class', axis = 1) #everything except class
        y = iris['class']
        x_train, x_test, y_train, y_test = train_test_split(x, y, test_size = 0.2, random_state = None)
        model.fit(x_train, y_train)
        prediction = model.predict(x_test)
        arr_acc.append(accuracy_score(y_test, prediction))
    return np.std(arr_acc)
    

iris = load_dataset()   
print(iris)
summarize_dataset()
print_plot_univariate()
print_plot_multivariate()
my_print_and_test_models()