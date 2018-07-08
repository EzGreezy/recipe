#!/usr/bin/env python
# -*- coding: utf-8 -*-

import re

f = open('recipes.xml','r')
s = open('recipes2.txt', 'w')
t = open('titles.txt', 'w')

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

for line in f:

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
            s.write(title + '\n')
            t.write(title + '\n')
            for add in addThese:
                s.write(add + '\n')
            s.write(text.replace('</page>', '') + '\n')
            numberOfRecipes += 1
        title = ""
        addThese = []
        shouldCapture = False
        hasDescription = False
        hasIngredients = False
        hasDirections = False
        hasCategory = False
    if hasIngredients:
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

f.close()
s.close()
t.close()
print("The number of recipes added was:", str(numberOfRecipes))
print("The total number of unique categories:", len(set(categories)))
print(sorted(set(categories)))
