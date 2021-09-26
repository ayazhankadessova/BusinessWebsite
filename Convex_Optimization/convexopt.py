import matplotlib.pyplot as plt
import numpy as np
from scipy.optimize import linprog
from scipy.optimize import minimize_scalar

#1)INTODUCTION

f = lambda x : (x - 1)**4 + x**2
f_prime = lambda x : 2*x + 4*(x-1)**3

#1.1)PLOT F Function 
def plot_f_function():
    x = np.arange(-1, 3, 0.1) 
    y=f(x) 
    zero_y = np.arange(-1,3,0.1) * 0
    plt.plot(x,zero_y, 'red', linewidth=1)
    plt.plot(x,y,'blue', linewidth=1)
    plt.plot(0.41, f(0.41), 'go')

#1.2)PLOT F_Prime Function 
def plot_fprime_function():
    x = np.arange(-1, 3, 0.1) 
    y = f_prime(x) 
    zero_y = np.arange(-1,3,0.1) * 0
    plt.plot(x,zero_y, 'red', linewidth=1)
    plt.plot(x,y,'green', linewidth=1)

#1.3)Simple dichotomous algorithm (bisection method) to find the zero of a function
def find_root(f, a, b):
    
    if f(a) * f(b) > 0:
        print("You have not assumed right a and b\n")
        return 0
    
    c = (a+b)/2
    
    while abs(f(c)) > 0.01:
        
        if (f(a)*f(c) < 0):
            b = c
        else:
            a = c
        print(c)
        c = (a+b)/2
  
    print("The values of f_prime's root is : ","%.4f"%c)

#1.4) Check against Brent's method 

res = minimize_scalar(f, method='brent')
print('x_min: %.02f, f(x_min): %.02f' % (res.x, res.fun))

# plot curve
x = np.linspace(res.x - 1, res.x + 1, 100)
y = [f(val) for val in x]
plt.plot(x, y, color='blue', label='f')

# plot optima
plt.scatter(res.x, res.fun, color='red', marker='x', label='Minimum')

plt.grid()
plt.legend(loc = 1)
print("CORRECT")

#2.1)Gradient Descent Methods

def gradient_descent(f, f_prime, start, learning_rate = 0.1): #default learning rate = 0.1. If bigger, might skip x
    
    x = start 
    
    for _ in range(100):
        x = x - f_prime(x) * learning_rate #f_prime(x) will show the slope
        
    return x

#2.2)Gradient Descent Driver
def driver_grad_descdent():
    start = -1
    x_min = gradient_descent(f, f_prime, start, 0.01)
    f_min = f(x_min)

    print("xmin: %0.2f, f(x_min): %0.2f" % (x_min, f_min))

#2.3) Simplex Method
#Create matrixes. Left of = sign -> Matrix A
#Right of = sign -> Matrix B
def simplex_method():
    A = np.array([[2, 1], [-4, 5], [1, -2]]) 
    B = np.array([10, 8, 3])
    c = np.array([1, 2])

    #x, y ≥ 0
    # x_range = (0, None)
    # y_range = (0, None)


    def solve_linear_problem(A, B, c):
        maximized = linprog(c, A, B)
        return maximized #x = [3., 4.] gives max func value(-11), and if we *1 the c array, max = 11

    optimal_value, optimal_argx, optimal_argy = solve_linear_problem(A, B, c)['fun'], solve_linear_problem(A, B, c)['x'][0], solve_linear_problem(A, B, c)['x'][1]

    print("The maximum value is: ", optimal_value, " and is reached for x = ", optimal_argx , " and y = ", optimal_argy)


# Driver code
# Initial values assumed, you can change them
plot_f_function()
plot_fprime_function()
find_root(f_prime, 0, 1)
driver_grad_descdent()
simplex_method()
