# 1. fázis: Build
FROM node:lts-alpine as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
# Itt fogja beégetni a VITE_API_BASE_URL=/api értéket a .env.production-ből
RUN npm run build

# 2. fázis: Production
FROM nginx:stable-alpine
COPY --from=build-stage /app/dist /usr/share/nginx/html
# Bemásoljuk az előbb létrehozott konfigot
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]