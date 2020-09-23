# build stage
FROM node:lts-alpine as build-stage
#RUN npm install -g http-server
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY ./src ./src
COPY ./static ./static
COPY ./index.html .
COPY ./.babelrc .
COPY ./webpack.config.js .
RUN npm run build

# production stage
FROM nginx:stable-alpine as production-stage
COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

# http-server
#EXPOSE 8080
#CMD [ "http-server", "./dist", "-a", "localhost", "-p", "8080", "-c-1" ]
