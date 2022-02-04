FROM node:16.13.1-alpine as node

WORKDIR /app

COPY package*.json ./
COPY src src
COPY babel.config ./
COPY public public


