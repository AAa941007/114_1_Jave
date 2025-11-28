Purpose
- Help enable UTF-8 output in PowerShell (no code changes required)

Files created
- `scripts\enable-powershell-utf8.ps1` : PowerShell script that updates your PowerShell profile and sets `_JAVA_OPTIONS` for the current user.
- `scripts\TestUnicode.java` : Small Java program to verify emoji / special-character output.

Quick usage
1. In PowerShell (run as normal user):
   powershell -ExecutionPolicy Bypass -File .\scripts\enable-powershell-utf8.ps1

2. Restart your PowerShell or Windows Terminal session.

3. Compile and run the test program (from workspace root):
   javac scripts\TestUnicode.java
   java -cp scripts TestUnicode

If output shows the symbols correctly, your terminal and Java are configured.

Notes & troubleshooting
- If you still see boxes or ? instead of emoji, the problem is most likely the terminal font. Use `Windows Terminal` and select a font that supports emoji (e.g., `Segoe UI Emoji`, `Cascadia Code PL`, or `Segoe UI Symbol`).
- Older conhost (the classic console window) may not display colored emoji; it may show monochrome symbols or fallbacks.
- To remove the Java env var later:
  In PowerShell: `[Environment]::SetEnvironmentVariable("_JAVA_OPTIONS", $null, "User")`

Recommended terminal and font
- Use `Windows Terminal` (Microsoft Store) for best Unicode/emojis support.
- In Windows Terminal settings, set profile font to a font that supports emoji (try `Cascadia Code PL` or `Segoe UI Emoji`).

If you want, I can:
- Auto-run the script for you (I cannot execute it from the repo — you must run locally), or
- Update your IDE run configuration instructions to pass `-Dfile.encoding=UTF-8` directly to the JVM.
