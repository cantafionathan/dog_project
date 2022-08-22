# My Personal Project

## Show Dog

**Show Dog** is a game where users take care of their dog and win money by going to the dog show!

What a user can do:
- feed and maintain the health of their dog
- take their dog to dog shows (to earn money)
- buy food for their dog

In the game, users will be greeted by their *dog* which they must name and grow to love. By 
*feeding* and *playing* with their dog users will maintain their dog's health. If that 
health reaches **zero**, their dog will unfortunately cease to be with us. If instead the
user manages to take good care of their dog, they can go to *dog shows* to earn *money* 
with which they can buy treats and other *food* items to feed their dog.

This program will be used by people who want to play a low stress game. Perhaps
it can even simulate the experience of taking care of a dog to a certain extent.
Everyone needs companionship, and my hope is that this game can create a sense
of companionship.

This program is of interest to me because I love animals, and especially dogs!
I wanted to write a program that could simulate the experience of what it's
like to raise a dog. Of course raising a dog has many more responsibilities
than what I was able to implement in this program, but I hope that it can 
at least be fun!

## User Stories

- As a user, I want to be able to name my dog
- As a user, I want to be able to feed my dog food
- As a user, I want to be able to play with my dog
- As a user, I want to be able to view all the food in my inventory
- As a user, I want to be able to view how much money different food items cost
- As a user, I want to be able to buy food for my dog and have it added to my inventory
- As a user, I want to be able to view my balance
- As a user, I want to be able to view the hunger and health of my dog
- As a user, I want to be able to take my dog to the dog show and earn money
- As a user, I want to be able to save the state of my game (including the health 
and hunger of my dog, the food in my inventory, and my balance)
- As a user, I want to be able to load the state of my game (including the health
and hunger of my dog, the food in my inventory, and my balance)

## Phase 4: Task 2
Thu Nov 25 13:43:59 PST 2021
Bought MilkBone

Thu Nov 25 13:44:01 PST 2021
Played with Toby

Thu Nov 25 13:44:04 PST 2021
Took Toby to the Dog Show

Thu Nov 25 13:44:06 PST 2021
Took Toby to the Dog Show

Thu Nov 25 13:44:07 PST 2021
Played with Toby

Thu Nov 25 13:44:11 PST 2021
Fed Toby MilkBone

## Phase 4: Task 3

I feel like the design could be improved majorly
if instead of giving the GUI class the Dog, JsonWriter,
and JsonReader fields I gave them to the GameManager
class. This is the purpose of the GameManager class anyways - 
to be a central hub for the whole program. Other than this
I am very happy with my design and class relationships.
