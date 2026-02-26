# Plain Java AssemblyAI Client

This is a plain Java project demonstrating how to use the AssemblyAI API for audio transcription.

- No Maven
- No Gradle
- No frameworks
- 🎯 Just Java (javac / java)
  All required dependencies are included so the project works immediately after cloning.

## 📁 Project Structure

```txt
├── src/
│   └── Main.java
│
├── lib/
│   ├── gson-2.10.1.jar
│   └── java-dotenv-5.2.2.jar
│
├── .env.example
├── .gitignore
└── README.md
```

## 🔧 Requirements

- Java 11 or newer
- Internet connection (to access AssemblyAI API)

## 🔑 Configuration

- register, if not yet at
  [link](https://www.assemblyai.com/)

```text
Replace `YOUR_ASSEMBLYAI_API_KEY` in `Main.java` with your own API key.
```

- use your own source of audio for transcription, put into

```java
private static final String AUDIO = "https://www.your-audio.source";
```

## ▶️ Compile & Run

```bash
javac -cp "lib/*" src/Main.java
java  -cp "lib/*:src" Main
```

## 📦 Dependencies

This project intentionally avoids a build system.

- Included library (committed under /lib):
- `Gson` – JSON serialization/deserialization

They are committed so the project can be run without any setup.

## 🧠 Design Notes

- HTTP calls use java.net.http.HttpClient
- JSON parsing uses Gson
- Polling uses `ScheduledExecutorService`
- The project favors clarity and minimalism over frameworks

## 📜 License

This project is provided for learning and experimentation purposes