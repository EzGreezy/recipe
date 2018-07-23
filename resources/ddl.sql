CREATE TABLE IF NOT EXISTS `categories` (
  `id` INTEGER NOT NULL,
  `name` TEXT,
  PRIMARY KEY(`id`)
)

CREATE TABLE IF NOT EXISTS `categories_map` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  `recipe_id` INTEGER NOT NULL,
  `category_id` INTEGER NOT NULL,
  FOREIGN KEY(`category_id`) REFERENCES `categories`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION ,
  FOREIGN KEY(`recipe_id`) REFERENCES `recipes`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
)

CREATE TABLE IF NOT EXISTS `ingredients` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  `name` TEXT
)

CREATE TABLE IF NOT EXISTS `ingredients_map` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  `ingredient_id` INTEGER NOT NULL,
  `recipe_item_id` INTEGER NOT NULL,
  FOREIGN KEY(`recipe_item_id`) REFERENCES `recipe_item`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION,
  FOREIGN KEY(`ingredient_id`) REFERENCES `ingredients`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
)

CREATE TABLE IF NOT EXISTS `recipes` (
  `id` INTEGER NOT NULL,
  `title` TEXT,
  `directions` TEXT,
  PRIMARY KEY(`id`)
)

CREATE TABLE IF NOT EXISTS `recipe_item` (
  `id` INTEGER NOT NULL,
  `recipe_id` INTEGER NOT NULL,
  `description` TEXT, PRIMARY KEY(`id`),
  FOREIGN KEY(`recipe_id`) REFERENCES `recipes`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
)

CREATE TABLE IF NOT EXISTS `shopping_list` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  `ingredient_id` INTEGER, `ingredient_item` TEXT,
  `recipe_id` INTEGER, `remove_string` TEXT,
  FOREIGN KEY(`ingredient_id`) REFERENCES `ingredients`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION,
  FOREIGN KEY(`recipe_id`) REFERENCES `recipes`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
)