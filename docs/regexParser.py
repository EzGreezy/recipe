#!/usr/bin/env python
# -*- coding: utf-8 -*-

import re
import readchar

recipesFile = open('recipes.xml','r')
recipesOutput = open('recipes.txt', 'w')
titlesOutput = open('titles.txt', 'w')
ingredientsFile = open('ingredients.txt', 'w')

shouldCapture = False

isRecipe = False
hasIngredients = False
hasDirections = False
hasCategory = False
hasDescription = False
numberOfRecipes = 0
numberWithCategories = 0
fractions = {'½': '.5', '⅓':'.334', '⅔':'.667', '¼':'.25', '¾':'.75', '⅕':'.2',
             '⅖':'.4', '⅗':'.6', '⅘':'.8', '⅙':'.167', '⅚':'.834', '⅐':'.143',
             '⅛': '.125', '⅜': '.375', '⅝':'.625', '⅞':'.875', '⅑':'.111',
             '⅒':'.1'}
addThese = []
categories = []
ingredients = set([])
regex = re.compile('\[\[[^a-zA-Z]\]\]')

for line in recipesFile:

    text = line.strip()

    if '<title>' in text:
        title = text
        title = title.replace('<title>', '')
        title = title.replace('</title>', '')
        shouldCapture = True
    if 'Category:' in text:
        hasCategory = True
    if 'Description' in text:
        hasDescription = True
    if 'Ingredients' in text:
        hasIngredients = True
    if 'Directions' in text:
        hasDirections = True

    if '</page>' in text:
        # May include recipes having hasDescription, hasIngredients,
        #                            hasDirections,  hasCategory
        if hasIngredients and hasDirections:
            for i in range(len(addThese)):
                for key, value in fractions.items():
                    if key in addThese[i]:
                        addThese[i] = re.sub(key, fractions[key], addThese[i])
            recipesOutput.write(title + '\n')
            titlesOutput.write(title + '\n')
            atDirections = False
            for addLine in addThese:
                if not atDirections:
                    if '==Directions==' in addLine:
                        atDirections = True
                        recipesOutput.write(addLine + '\n')
                    else:
                        findIngs = re.findall(r'\[\[[a-z |]+\]\]', addLine)
                        for w in findIngs:
                            ingredients.add(w) # .lower() ?
                        recipesOutput.write(addLine.lower().strip() + '\n')
                else:
                    recipesOutput.write(addLine + '\n')
            recipesOutput.write(text.replace('</page>', '') + '\n')
            numberOfRecipes += 1
        title = ""
        addThese = []
        shouldCapture = False
        hasDescription = False
        hasIngredients = False
        hasDirections = False
        hasCategory = False
    if hasDescription:
        if "[[File" not in text and "Videos" not in text:
            text = re.sub(r"<.*>", "", text)
            text = re.sub(r"'''", "", text)
            if 'Category:' in text:
                category = re.sub(r'\[\[Category:', '', text)
                category = re.sub(r'\]\]', '', category)
                categories.append(category)
            if '</text>' in text:
                text = text.replace('</text>', '')
            addThese.append(text)


"""
print("The number of recipes added was:", str(numberOfRecipes))
print("The total number of unique categories:", len(set(categories)))
print("Number of ingredients:", len(ingredients))
"""

ingredientsList = sorted(list(ingredients))
newIngredientsList = []
for i in ingredientsList:
    ing = i.replace('[[', '').replace(']]', '')
    ingredientsFile.write(ing + '\n')
    newIngredientsList.append(ing)

recipesFile.close()
ingredientsFile.close()
recipesOutput.close()
titlesOutput.close()

recipeItemsWOIngredient = []
recipes = open('recipes.txt', 'r')

isIngredients = False
for r in recipes:
    text = r
    text = text.lower()
    if re.search(r'[= ]+[Ii]ngredients[= ]+', text):
        isIngredients = True
    elif re.search(r'[= ]+[Dd]irections[= ]+', text):
        isIngredients = False
    elif re.search(r'[= ]+[Dd]escription[= ]+', text):
        isIngredients = False
    elif isIngredients and re.search('[a-zA-Z]', text):
        for i in range(len(newIngredientsList)):
            if newIngredientsList[i] in text:
                break
            if i == len(newIngredientsList) - 1:
                print(text)
                recipeItemsWOIngredient.append(text)
"""
ris = getRecipeItems()
for i in range(100):
    print(next(ris))"""
recipes.close()
print(sorted(recipeItemsWOIngredient))
print(len(recipeItemsWOIngredient))
# print(len(ingredients))
