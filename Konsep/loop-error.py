l = [1 , 2 , 3 , 0 , 0 , 1]

# konsep bagaimana loop bisa mengalami "list index out of range error"
# https://stackoverflow.com/questions/1798796/python-list-index-out-of-range-error

for i in range(0,len(l)):
       if l[i] != 0:
           l.pop(i)

# i = 0
# while i < len(l):
#   if l[i] == 0:
#       l.pop(i)
#   else:
#       i += 1