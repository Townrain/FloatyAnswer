# FloatyAnswer — Android Screenshot Assistant with DeepSeek Flash & VLM

<p align="center">
<a href="https://github.com/delayyyyyyy/FloatyAnswer/stargazers"><img alt="GitHub Stars" src="https://img.shields.io/github/stars/delayyyyyyy/FloatyAnswer"></a>
<a href="https://github.com/delayyyyyyy/FloatyAnswer/actions"><img alt="Build Status" src="https://img.shields.io/github/actions/workflow/status/delayyyyyyy/FloatyAnswer/android_build.yml"></a>
<a href="LICENSE"><img alt="License" src="https://img.shields.io/badge/License-AGPLv3-blue"></a>
</p>


> This repository is a fork of Townrain/FloatyAnswer, licensed under AGPL-3.0. Its earlier foundation is wb-hwang/AIAnswerer-Android, released under the MIT License. Thanks to both projects and their contributors. [Townrain/FloatyAnswer](https://github.com/Townrain/FloatyAnswer) · [wb-hwang/AIAnswerer-Android](https://github.com/wb-hwang/AIAnswerer-Android)

[中文](README.md) | [English](#english-user-guide)

## English User Guide

### App Introduction

FloatyAnswer is an open-source Android screenshot assistant for screenshot question answering through a floating overlay. It recognizes questions with local OCR or a vision-language model (VLM), then sends the recognized text to a large language model for an answer. This fork adds DeepSeek Flash vision support and improves custom OpenAI-compatible API configuration for practice and self-assessment.

### This fork's updates

- Added `deepseek-flash` to the built-in DeepSeek model list and vision capability rules, with support for the aliases `deepseek-v4-flash` and `deepseek-v4-flash-vision-exp`, so selected models can appear in the home screen's **VLM 模型** menu.
- Fixed custom OpenAI-compatible provider resolution so the selected model, API Host, and API Key stay associated with the same provider for recognition and answering.
- Added regression tests for vision-model recognition and custom providers, and made the DNS failure test reproducible with a simulated network error.
- Completed local unit-test validation with **904 tests — 785 passed, 119 skipped, and 0 failures**.
- Built a debug APK using the `com.hwb.aianswerer.debug` application ID. It can be installed alongside the release app and needs its own permission grants, API credentials, and model configuration.
- On-device screenshot capture and live API behavior still need verification.

### Feature Highlights

- 🖼️ Quick Screen Capture: One-click screenshot of the current screen, automatically focusing on the question area
- 📖 Screen Reading Mode: Directly read screen text through accessibility services without screenshots, faster speed
- 📝 Smart Text Recognition: Supports CJK/FR/DE/ES/PT local OCR + VLM cloud recognition for RU/AR
- 👁️ Vision Model Support: Can use vision models instead of OCR, suitable for noisy pages
- 🔍 Web Search Enhancement: LLM Tool Calling mode (the model decides when to search and may ask follow-up rounds)
- 🤖 AI Real-time Answering: Generate analysis based on question types and automatically copy answers
- 📋 Batch Answering: When multiple questions are in a screenshot, search each question and return all answers
- ⚡ Parallel Answering: Support concurrent processing in multi-question mode, significantly improving answering speed
- 💬 Floating Window Operation: Complete screenshots, preview, and submission without switching apps
- 🔒 Local Control: Custom API Key, start/stop network requests anytime
- 🎨 8 Built-in Themes: Including Claude Warm coral tones + JSON import for custom themes, floating button auto-colors with theme
- 🌐 Multilingual UI: Chinese/English interface + 11 AI output languages (CN/EN/JP/FR/DE/ES/PT/KO/RU/AR + adaptive)

### How It Works

```
┌──────────┐   ┌──────────────────┐   ┌──────────────┐   ┌───────────────┐   ┌──────────┐
│ Floating │──▶│ Capture: Screenshot│──▶│ OCR / VLM    │──▶│ LLM Answer    │──▶│ Answer   │
│  Pill    │   │ / Screen Read     │   │ (Text Recog.)│   │ + Web Search  │   │ Card     │
│ (Always) │   │ / Record / Multi  │   │              │   │               │   │(Collapse)│
└──────────┘   └──────────────────┘   └──────────────┘   └───────────────┘   └──────────┘
```

With VLM enabled, the screenshot is uploaded to the selected vision model, which returns recognized text. The selected language model then uses that text to answer the question.

### Installation and Preparation

1. Use a device with Android 10 or above and keep the network connected.
2. Install the provided APK file; first installation requires allowing apps from unknown sources according to system prompts.
3. On first launch, follow the on-screen prompts to grant necessary permissions such as floating window, screen recording (or accessibility), and notifications.
4. Configure your LLM model in Settings (see "Quick Start" below).

### Quick Start

**Step 1 — Configure your provider and models**

1. Open **Settings → 模型厂商 (Model providers)**.
2. Expand the **DeepSeek** or **自定义 OpenAI 兼容 (Custom OpenAI-compatible)** provider card. For the official DeepSeek API, use `https://api.deepseek.com/v1` as the **API Host**, enter your **API Key**, and enable the provider's switch. The app also accepts `https://api.deepseek.com` and appends the Chat Completions path.
3. Tap **+ 添加模型 (Add model)**. Select `deepseek-flash` from the list. If it is missing, tap **↻** to fetch the provider's available models, then select it. Model selection uses the list loaded in this dialog.
4. Tap **确认 (Confirm)**, then **保存配置 (Save configuration)** at the bottom of the current provider card. You can also use the card's connection test to check your credentials.
5. Return to the home screen. Select the answering model under **语言模型 (Language model)** and the recognition model under **VLM 模型 (VLM model)**. Select `deepseek-flash` in both menus to use it for both stages.

**Step 2 — Start answering**

1. On the home screen, tap **Start Answer Mode**.
2. Grant the requested permissions. Floating-window permission allows the floating pill and answer cards to appear over other apps. Follow the system's screen-recording permission prompts for screenshot mode, or enable the accessibility service for Screen Reading mode.
3. When the floating pill appears, long-press it to open the quick panel and enable the **VLM** switch for vision recognition.

**Step 3 — Answer questions**

1. Open the question page you want to capture.
2. Tap the floating pill. With VLM enabled, the app captures a screenshot, sends it to the VLM for text recognition, and sends the resulting text to the selected language model for answering.
3. Read the answer card and tap it to expand the full explanation. Automatic clipboard copying follows your settings.

> 💡 If you need to correct the recognized text manually, edit it on the confirmation page before submitting.

### Four Answering Modes

#### 1. Screenshot Mode (default)

Tap the floating pill → capture the current screen → local OCR or VLM text recognition → LLM answering. Suitable for most scenarios.

Combine with **crop modes** (Settings → Answer Settings):

| Crop Mode | Behavior |
|-----------|----------|
| Full Screen | Recognize the whole screen; best when the question fills most of it |
| Per-question Crop | Select each question separately; the selection is saved for reuse |
| Single Crop | Select a region once; every capture only recognizes that fixed region |

#### 2. Screen Reading Mode (accessibility, faster)

Reads the on-screen text directly through the **accessibility service** — no screenshot needed, faster, and no screen-recording permission required.

How to enable: switch the capture mode card on the home screen to "Screen Reading" → jump to system settings and enable the accessibility service for FloatyAnswer → return.

> Note: This mode reads text nodes, so it cannot handle image-based questions; enable the Vision Model (VLM) as a supplement in that case.

#### 3. Recording Mode (batch answering)

For multi-question pages / continuous practice: capture multiple screens in a row, automatically deduplicated and recognized per question, then get all answers at once when you stop.

1. Long-press the floating pill to open the quick panel → tap **Record** to start;
2. Stay on the question page; each pill tap captures one screen — capture as many questions as you like;
3. Tap **Record** again to stop (or close the floating window) and wait for the unified analysis;
4. All answers are displayed in order in the answer card, and the full answer set is copied to the clipboard.

**Parallel answering** is automatically enabled in multi-question mode (concurrency 1–10, adjustable in Settings) for a significant speed boost.

#### 4. Multi-image Mode

Combine multiple screenshots into a single question: useful for cross-page questions or sending several related questions to the AI at once.

1. Tap **Multi-image** in the quick panel to start;
2. Each pill tap adds one screen; repeat until done;
3. Tap **Multi-image** again to finish and submit — the AI merges and analyzes everything, returning all answers together.

### Floating Window Guide

| Gesture | Action |
|---------|--------|
| Tap the pill | Capture and answer (or capture one question in recording mode) |
| Long-press the pill | Open/close the quick panel (long-press duration configurable, 300ms–3000ms) |
| Drag the pill | Move it anywhere; snaps to the screen edge when released |
| Quick panel | Toggles for VLM, Web Search, Deep Thinking, Record, Multi-image |

Answer cards are **collapsible**: collapsed shows only the answer summary (e.g. choice letters); tap to expand the full explanation.

### Supported Question Types

- Multiple Choice: Identify question stem and options, mark recommended answers and provide reasons
- Fill-in-the-blank: Generate concise answers, suitable for multi-blank questions
- Essay Questions: Provide structured solutions or key-point analysis

### Web Search

Supports LLM Tool Calling web search: when enabled, the model decides whether to search on its own (for current-affairs or recent-news questions), with multi-round follow-ups. 10 search providers are built in (Tavily / Bocha / Zhipu / Exa etc.), configurable under Settings → Web Search.

> If the active model does not support function calling, web search is automatically disabled and a hint is shown in Settings.

### Usage Tips

- Keep screenshots clear and centered, avoid complex backgrounds to improve OCR accuracy.
- If you need to pause network requests, temporarily disconnect from the network or disable AI answers in the settings page.
- After answer generation, you can tap the floating pill again to refresh the question for continuous practice.
- Enable the Vision Model (VLM) for image-based or noisy questions.
- In Settings → Appearance, you can adjust pill size, opacity, icon scale, and choose from 8 built-in themes or import a custom JSON theme.

### FAQ

- **Missing permissions prompt?** Go to system settings and search for "floating window", "screen recording", "accessibility", etc., and enable the relevant permissions manually.
- **The "Start Answer Mode" button is greyed out?** Open Settings → 模型厂商 (Model providers), enable a provider, enter its API Key, select a model, and save the configuration.
- **DeepSeek Flash is missing from the VLM list?** Add `deepseek-flash` under the DeepSeek or custom OpenAI-compatible provider, save the configuration, and select it in the home screen's VLM menu. Use **↻** in the Add model dialog to refresh an older model list. This fork includes the model's vision capability rules.
- **Inaccurate recognition?** Manually correct the text on the confirmation page, or take a new screenshot; enable the Vision Model (VLM) for noisy images.
- **AI not responding?** Check the network, confirm the API Key is valid, and make sure your model account has sufficient balance; use "Test Connection" in Settings to diagnose quickly.
- **Screen Reading mode not working?** Confirm the accessibility service is enabled and not restricted by the system's battery-saving policy.
- **Web search not working?** Check the search toggle and provider API Key in Settings; if the model doesn't support function calling, this feature is automatically disabled.
- **The answer card is blocking the screen?** Tap it to collapse to a summary, or close it; the floating pill can be dragged anywhere.
- **Worried about privacy?** Configuration is stored locally, and API Keys use encrypted storage. Recognized text is sent to the configured language-model service when answering. VLM recognition also uploads screenshots to the configured vision-model service.

### Privacy and Disclaimer

- The application sends recognized text to the selected language-model service. Enabling VLM recognition also sends screenshots to the selected vision-model service. Please consider the contents of the captured screen before submitting it.
- API requests may incur charges (e.g., DeepSeek), please pay attention to usage frequency.
- This application is only for learning and research purposes. Please comply with exam discipline and laws and regulations. Any consequences of illegal use are at your own risk.

### Tech Stack

| Category | Technology |
|----------|------------|
| Language | Kotlin 2.0.21 |
| UI | Jetpack Compose + Material3 |
| OCR | Google ML Kit (Chinese/Latin/Japanese/Korean) + VLM fallback |
| Vision Model | OpenAI-compatible API (DeepSeek Flash / GPT-4o, etc.) |
| Web Search | Multi-provider (Tavily/Bocha/Zhipu/ExaMCP etc., 10 providers) |
| Network | OkHttp 4.12.0 |
| Storage | MMKV + EncryptedSharedPreferences |
| Build | Gradle 8.9 + Android Gradle Plugin 8.7.3 |

### Project Structure

```
com.hwb.aianswerer/
├── BaseActivity.kt            # Unified language configuration base class
├── MyApplication.kt           # Application initialization
├── MainActivity.kt            # Main interface (permission management, answer settings)
├── FloatingWindowService.kt   # Floating window core service (multi-window coordination)
├── FloatingWindowManager.kt   # Window management (A/B/C/D independent windows)
├── FloatingWindowViewModel.kt # Floating window state management
├── SettingsService.kt         # Centralized settings reader
├── CaptureHandler.kt          # Screenshot → crop → recognition pipeline
├── CapturePipeline.kt         # Core recognition pipeline (OCR→VLM→LLM)
├── RecordingCoordinator.kt    # Recording mode coordinator
├── ImageCollector.kt          # Image collector
├── AnswerFetcher.kt           # Answer fetcher
├── FloatingAnswerCard.kt      # Answer card component
├── ConfirmTextActivity.kt     # Recognition text confirmation/editing
├── ImageCropActivity.kt       # Image cropping (four-corner drag)
├── SettingsActivity.kt        # General settings
├── ModelSettingsActivity.kt   # API model configuration
├── AboutActivity.kt           # About page
├── Constants.kt               # Constants · prompt assembly · i18n routing hub
├── api/
│   ├── OpenAIClient.kt        # OpenAI compatible API client
│   ├── search/                 # Web search module
│   │   ├── WebSearchProviders.kt # Multi-provider web search
│   │   ├── WebSearchClientFactory.kt # Provider factory
│   │   ├── WebSearchProvider.kt # Search provider base class
│   │   └── WebSearchToolExecutor.kt # LLM tool-calling search executor
│   └── vision/                # Vision model module
│       ├── VisionProvider.kt
│       ├── VisionProviderFactory.kt
│       ├── VisionFilterResult.kt
│       └── OpenAIVisionProvider.kt
├── config/
│   ├── AppConfig.kt           # Configuration facade (MMKV + encrypted storage)
│   ├── ApiConfig.kt           # API configuration
│   ├── UIConfig.kt            # UI configuration
│   ├── VisionConfig.kt        # Vision model configuration
│   └── ConfigStorage.kt       # MMKV storage key definitions
├── models/                    # Data models (ChatMessage, ToolSpec, ToolCall, ModelCapabilityChecker etc.)
├── providers/                 # Model provider management
│   ├── ProviderStorage.kt
│   ├── ProviderConfigResolver.kt
│   └── ProviderSyncManager.kt
├── ui/
│   ├── components/            # Shared Compose components
│   │   ├── PillButtonCard.kt  # Floating pill + long-press gesture
│   │   ├── QuickToggles.kt    # Quick-toggle panel
│   │   ├── FloatingWindowContent.kt # Window A/B/C/D Composables
│   │   ├── FloatingAnswerCard.kt    # Answer card
│   │   ├── RecordingResultCard.kt   # Recording result card
│   │   ├── AnswerCard.kt      # Single answer card
│   │   ├── CtaBar.kt          # CTA button (with model detection)
│   │   └── ...                # Other components
│   ├── pages/                 # Pages
│   │   ├── HomePage.kt        # Home page
│   │   ├── SettingsPage.kt    # Settings page
│   │   └── WebSearchPage.kt   # Web search settings
│   ├── dialogs/               # Dialogs
│   ├── icons/                 # Local icon definitions
│   └── theme/                 # Material3 theme
└── utils/
    ├── AppLog.kt              # Unified logging tool
    ├── ClipboardUtil.kt       # Clipboard utility
    ├── ImageCropUtil.kt       # Image cropping utility
    └── LanguageUtil.kt        # Language switching utility
```

### Changelog

See [CHANGELOG.md](CHANGELOG.md) or [变更日志](变更日志.md).

### License

This project is released under the GNU Affero General Public License v3.0. [License](LICENSE)
