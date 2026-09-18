---

# **ArizonaTweetBot**
Automated AI‑powered tweeting engine for Arizona Wildcats sports content. The bot generates hype, game‑day reactions, and general team engagement posts using Azure OpenAI and posts them directly to X via the Twitter API.

---

## **Overview**
ArizonaTweetBot is a fully automated posting system built with Spring Boot, Azure OpenAI, and the X API. It selects a posting category, generates a tweet using prompt templates, and publishes it to X on a schedule. When Arizona is actively playing in any sport, the bot automatically switches to **game‑aware tweeting** and posts live‑context hype.

---

## **Key Features**
- AI‑generated Wildcats tweets  
- Game‑aware logic for live sports  
- Category‑based prompt templates  
- Automated scheduling  
- X API integration  
- MongoDB logging  

---

## **Architecture**
- Spring Boot backend  
- AIContentService — Azure OpenAI prompting  
- PostingService — X API publishing  
- SchedulerService — cron‑driven posting  
- MongoDB — tweet logs and analytics  
- Prompt templates — tone and category control  

---

## **How It Works**
1. Scheduler triggers a posting event  
2. Bot checks if Arizona is currently playing  
3. If a game is active → generate a **game‑specific** tweet  
4. If not → select a category and generate a standard hype tweet  
5. AIContentService sends prompt to Azure OpenAI  
6. PostingService publishes the tweet  
7. MongoDB logs the result  

---

## **Tech Stack**
- Java 17  
- Spring Boot  
- Azure OpenAI  
- Twitter/X API v2  
- MongoDB Atlas  
- Render / Railway deployment  

---

## **Environment Variables**
```
# Twitter / X API
export TWITTER_API_KEY="<your_twitter_api_key>"
export TWITTER_API_SECRET="<your_twitter_api_secret>"
export TWITTER_ACCESS_TOKEN="<your_twitter_access_token>"
export TWITTER_ACCESS_SECRET="<your_twitter_access_secret>"
export TWITTER_BASE_URL="https://api.x.com/2"
export TWITTER_USER_ID="<your_twitter_user_id>"

# Gemini API
export GEMINI_API_KEY="<your_gemini_api_key>"

# Azure OpenAI
export AZURE_OPEN_API_KEY="<your_azure_openai_key>"
export AZURE_OPEN_AI_URL="<your_azure_openai_endpoint>"

# MongoDB
export SPRING_MONGODB_URI="<your_mongodb_connection_string>"
```

---

## **Local Development**
```
./mvnw spring-boot:run
```

Ensure all environment variables are exported before running.

---

## **Prompt Template Strategy**
The bot uses category‑based prompt templates to maintain consistent tone:

- Pregame hype  
- In‑game reactions  
- Recruiting buzz  
- Wildcats fun facts  
- Rivalry energy  

---

## **Game Detection Logic**
The bot checks a sports schedule API to determine:

- Whether Arizona is currently playing  
- Sport type  
- Opponent  
- Game status (pregame, live, final)  

If a game is live, the bot switches to **real‑time hype mode**.

---

## **Deployment**
Supported deployment targets:

- Render  
- Railway  
- Azure App Service  
- Docker on any cloud VM  

Runs as an always‑on worker with scheduled tasks.

---

## **Future Enhancements**
- Real‑time score ingestion  
- Auto‑generated post‑game summaries  
- Image generation  
- Fan engagement replies  
- Multi‑team support  

---

## **Contributing**
Pull requests welcome. Open an issue for feature requests or bug reports.

---

## **License**
MIT License.

---

If you want, I can also generate:

- **an .env.example file**  
- **setup instructions**  
- **architecture diagram text**  

Just pick one.
