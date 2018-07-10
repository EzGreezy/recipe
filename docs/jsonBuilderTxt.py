#!/usr/bin/env python
# -*- coding: utf-8 -*-
import re
import json

# {
#   "title" : {
#     "directions": "string",
#     "recipeIngredients" = {
#       "raw_ingredient_string": ["ingredient"]
#     },
#     "categories": ["category"]
#   },
#   "title" : {
#     ...
#   },
#   ...
# }
theRecipes = {"ĆZ": 0}

recipesFile = open('recipes.txt','r')
# ingredientsFile = open('ingredients.txt', 'r')
# ingredientsString = ingredientsFile.nextLine()
# print(ingredientsString)

try:
    while True:
        line = next(recipesFile)
        text = line.strip()
        title = ""
        recipeIngredients = {}
        ingredients = []
        directions = []
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
                    recipeIngredients[text[1:]] = ingredients
                    text = next(recipesFile).strip()
                    while len(text) < 3:
                        text = next(recipesFile).strip()
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
                    directions.append(text[1:])
                    text = next(recipesFile).strip()
                    while len(text) < 3:
                        text = next(recipesFile).strip()
            theRecipes[title] = {"recipeIngredients": recipeIngredients, "directions": directions}
            theRecipes["ĆZ"] += 1
except(StopIteration):
    print("Dont worry")
recipesFile.close()
# ingredientsFile.close()

with open('recipeJSON.json', 'w') as fp:
    json.dump(theRecipes, fp, sort_keys=True, indent=4, ensure_ascii=False)
