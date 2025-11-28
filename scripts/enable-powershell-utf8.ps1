# Script: enable-powershell-utf8.ps1
# Purpose: Add UTF-8 settings to the current user's PowerShell profile
# Usage: Run in PowerShell: .\scripts\enable-powershell-utf8.ps1

$profilePath = $PROFILE
if (-not (Test-Path -Path $profilePath)) {
    New-Item -ItemType File -Path $profilePath -Force | Out-Null
}

$lines = @(
    'chcp 65001 > $null',
    '[Console]::OutputEncoding = New-Object System.Text.UTF8Encoding($false)'
)

foreach ($line in $lines) {
    $exists = Select-String -Path $profilePath -Pattern ([regex]::Escape($line)) -SimpleMatch -Quiet
    if (-not $exists) {
        Add-Content -Path $profilePath -Value $line
    }
}

# Set JVM default encoding for current user so Java programs prefer UTF-8
try {
    [Environment]::SetEnvironmentVariable("_JAVA_OPTIONS","-Dfile.encoding=UTF-8","User")
    Write-Host "Set _JAVA_OPTIONS=-Dfile.encoding=UTF-8 (User scope)"
} catch {
    Write-Warning "Failed to set _JAVA_OPTIONS environment variable: $_"
}

Write-Host "PowerShell profile updated: $profilePath"
Write-Host "Please restart PowerShell / Windows Terminal to apply the changes."
Write-Host "If your system blocks script execution, run as: powershell -ExecutionPolicy Bypass -File .\scripts\enable-powershell-utf8.ps1"