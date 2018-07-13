#!/usr/bin/env python
# -*- coding: utf-8 -*-
import re
import json

# {
#   "title" : {
#     "directions": "string",
#     "recipeIngredients" = {
#       "ingredient": ["raw_ingredient_string"]
#     },
#     "categories": ["category"]
#   },
#   "title" : {
#     ...
#   },
#   ...
# }
theRecipes = []

recipesFile = open('recipes.txt','r')
# ingredientsFile = open('ingredients.txt', 'r')
# ingredientsString = ingredientsFile.nextLine()
# print(ingredientsString)

categoryBuilder = []
recipeBuilder = []
categoryMapBuilder = []
ingredientBuilder = []
recipeItemBuilder = []
itemIngredientMapBuilder = []

ingredientsSet = set([])
ingredientsIds = {}

try:
    while True:
        text = next(recipesFile).strip()
        if text == "==title==":
            while text != "==ingredients==":
                text = next(recipesFile).strip()
            while len(text) == 0:
                text = next(recipesFile).strip()
            while text[0] != "*":
                text = next(recipesFile).strip()
                while len(text) == 0:
                    text = next(recipesFile).strip()
            while len(text) < 3:
                text = next(recipesFile).strip()
            while text[0] == "*" or text[0:3] == "===":
                # TODO get sub ingredients later
                if text[0:3] == "===":
                    text = next(recipesFile).strip()
                    while len(text) < 3:
                        text = next(recipesFile).strip()
                else:
                    ingredients = re.findall(r'\[\[[a-zA-Z\uFFFF|]+\]\]', text)
                    if ingredients == []:
                        hasNone = True
                        break
                    for i in ingredients:
                        splitIngredients = i.split('|')
                        for i in splitIngredients:
                            ingredientsSet.add(i)
                    text = next(recipesFile).strip()
                    while len(text) < 3:
                        text = next(recipesFile).strip()
except(StopIteration):
    ingredients = sorted(list(ingredientsSet))
    for i in range(1,len(ingredients)+1):
        ingredientsIds[ingredients[i-1].lower().replace('[[', '').replace(']]', '')] = i

for key, val in ingredientsIds.items():
    ingredientBuilder.append({"id": val, "name": key})
categoriesSet = set([])
categoriesIds = {}

recipesFile.seek(0)
try:
    while True:
        text = next(recipesFile).strip()
        if text == "==title==":
            while not re.search(r'\[\[[Cc]ategory\:[a-zA-Z]+ [Rr]ecipes\]\]', text):
                text = next(recipesFile).strip()
        while text != "==end==":
            d = False
            while re.search(r'\[\[[Cc]ategory\:[a-zA-Z]+ [Rr]ecipes\]\]', text):
                d = True
                cats = re.findall(r'\[\[[Cc]ategory\:[a-zA-Z]+ [Rr]ecipes\]\]', text)
                for cat in cats:
                    category = cat.lower()
                    category = category.replace('[[category:', '').replace(' recipes]]', '')
                    categoriesSet.add(category)
                text = next(recipesFile).strip()
            if not d:
                text = next(recipesFile).strip()
except(StopIteration):
    categories = sorted(list(categoriesSet))
    for i in range(1,len(categories)+1):
        if categories[i-1] not in ingredientsIds:
            categoriesIds[categories[i-1]] = i

for key, val in categoriesIds.items():
    categoryBuilder.append({"id": val, "name": key})

recipeId = 1
ingredientMapId = 1
recipeItemId = 1
categoryMapId = 1


recipesFile.seek(0)
try:
    while True:
        text = next(recipesFile).strip()
        title = ""
        directions = ""
        recipeIngredients = []
        ingredients = []
        categories = []
        has = False
        if text == "==title==":
            title = next(recipesFile).strip()
            while text != "==ingredients==":
                text = next(recipesFile).strip()
            while len(text) == 0:
                text = next(recipesFile).strip()
            while text[0] != "*":
                text = next(recipesFile).strip()
                while len(text) == 0:
                    text = next(recipesFile).strip()
            while len(text) < 3:
                text = next(recipesFile).strip()
            while text[0] == "*" or text[0:3] == "===":
                # TODO get sub ingredients later
                if text[0:3] == "===":
                    text = next(recipesFile).strip()
                    while len(text) < 3:
                        text = next(recipesFile).strip()
                else:
                    ingredients = re.findall(r'\[\[[a-zA-Z\uFFFF]+\]\]', text)
                    if ingredients == []:
                        has = True
                        break
                    ingredientIDs = []
                    for j in ingredients:
                        splitIngredients = j.split('|')
                        # TODO make many to many ingredients and recipeItems
                        for i in splitIngredients:
                            itemIngredientMapBuilder.append({"id": ingredientMapId, "recipe_item_id": recipeItemId, "ingredient_id": ingredientsIds[i.lower().replace('[[', '').replace(']]', '')]})
                            ingredientMapId += 1
                    recipeItemBuilder.append({"id": recipeItemId, "description": text[2:]})
                    recipeItemId += 1
                    text = next(recipesFile).strip()
                    while len(text) < 3:
                        text = next(recipesFile).strip()
            if has:
                while text != "==directions==":
                    text = next(recipesFile).strip()
                while len(text) == 0:
                    text = next(recipesFile).strip()
                while text[0] != "#":
                    text = next(recipesFile).strip()
                    while len(text) == 0:
                        text = next(recipesFile).strip()
                while len(text) < 3:
                    text = next(recipesFile).strip()
                while text[0] == "#" or text[0:3] == "===":
                    # TODO get sub directions later
                    if text[0:3] == "===":
                        text = next(recipesFile).strip()
                        while len(text) < 3:
                            text = next(recipesFile).strip()
                    else:
                        directions = directions + '& ' + text[2:]
                        text = next(recipesFile).strip()
                        while len(text) < 3:
                            text = next(recipesFile).strip()
                while text != "==end==":
                    if re.search(r'\[\[[Cc]ategory\:[a-zA-Z]+ [Rr]ecipes\]\]', text):
                        cats = re.findall(r'\[\[[Cc]ategory\:[a-zA-Z]+ [Rr]ecipes\]\]', text)
                        for cat in cats:
                            c = cat.lower().replace('[[category:', '').replace(' recipes]]', '')
                            if c in categoriesIds:
                                categoryMapBuilder.append({"id": categoryMapId, "recipe_id": recipeId, "category_id": categoriesIds[c]})
                                categoryMapId += 1
                    text = next(recipesFile).strip()
                    recipeBuilder.append({"id": recipeId, "title": title, "directions": directions})
                    recipeId += 1
except(StopIteration):
    print("Dont worry")
recipesFile.close()
# ingredientsFile.close()

with open('category.json', 'w') as fp:
    json.dump(categoryBuilder, fp, sort_keys=True, indent=2, ensure_ascii=False)

with open('recipe.json', 'w') as fp:
    json.dump(recipeBuilder, fp, sort_keys=True, indent=2, ensure_ascii=False)

with open('categoryMap.json', 'w') as fp:
    json.dump(categoryMapBuilder, fp, sort_keys=True, indent=2, ensure_ascii=False)

with open('ingredient.json', 'w') as fp:
    json.dump(ingredientBuilder, fp, sort_keys=True, indent=2, ensure_ascii=False)

with open('recipeIngredient.json', 'w') as fp:
    json.dump(recipeItemBuilder, fp, sort_keys=True, indent=2, ensure_ascii=False)

with open('ingredientMap.json', 'w') as fp:
    json.dump(itemIngredientMapBuilder, fp, sort_keys=True, indent=2, ensure_ascii=False)
