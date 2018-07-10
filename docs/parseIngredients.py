ingredients = file('ingredients.txt', 'r')
saveFile = file('ingredientsPerLine.txt', 'w')
ingredientsList = []

for line in ingredients:
    if '|' in line:
        strings = line.split('|')
        for s in strings:
            if len(s) > 2:
                ingredientsList.append(s.strip())
    else:
        ingredientsList.append(line.strip())

for i in sorted(list(set(ingredientsList))):
    saveFile.write(' ' + i + ' ' + '\n')

ingredients.close()
saveFile.close()
