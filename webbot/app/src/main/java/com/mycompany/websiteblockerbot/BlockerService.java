package com.mycompany.websiteblockerbot;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.mycompany.websiteblockerbot.MainActivity;
import android.content.Context;
import android.widget.Toast;


public class BlockerService extends AccessibilityService {
    
    /*
    
    public class AppConstants {
        public static final String APP_NAME = "WebBlocker";
        public static final String APP_PACKAGE = "com.mycompany.websiteblockerbot";
    }
    
    */
    
    String[] blockedSites = {
        
        // ==== Pornhub Domains ====
        "pornhub.com", "www.pornhub.com", "m.pornhub.com", "pornhubpremium.com", "www.pornhubpremium.com",
        "phub.com", "www.phub.com", "pornhublive.com", "www.pornhublive.com",
        "pornhub.org", "pornhub.net", "pornhub.sex", "pornhub.love", "pornhub.red", "pornhub.page",
        "pornhubvideos.com", "pornhubvideo.com", "pornhub.cam", "pornhubstream.com",
        "pornhub.watch", "pornhubxxx.net", "pornhub.best", "phub.xxx", "phub.video", "phub.sex",
        "porn-hub.com", "pornhubx.com", "pornhubvip.com", "pornhubapp.com", "pornhubxvideos.com",

        // ==== XNXX Domains ====
        "xnxx.com", "www.xnxx.com", "m.xnxx.com", "xnxx.tv", "xnxx.net", "xnxx123.com", "xnxx69.net",
        "xnxx.live", "xnxx-free.com", "xnxx.today", "xnxx.cam", "xnxx.page", "xnxx.red", "xnxx.watch",
        "xnxx.best", "xnxxvideo.com", "xnxxvideos.com", "xnxx.sex", "xnxx.vip", "xnxx.pro", "xnxx1.com",
        "xnxxhub.com", "xnxxporn.net", "xnxxapp.com", "xnxxclub.com", "xxnx.com", "xnnx.com", "xnxxx.com",

        // ==== XVideos Domains ====
        "xvideos.com", "www.xvideos.com", "m.xvideos.com", "xvideos2.com", "xvideos3.com",
        "xvideos.net", "xvideos.xxx", "xvideos.cam", "xvideos.page", "xvideos.red",
        "xvideos123.com", "xvideos69.com", "xvideoshub.com", "xvideosporn.com", "xvideosex.net",
        "xvideosbest.com", "xvideosonline.com", "xvideostube.net", "xvideosmobile.com",
        "x-videos.com", "xvideoz.com", "xvidoes.com", "xxvideos.com", "xxxvideos.com",

        // ==== xHamster Domains ====
        "xhamster.com", "www.xhamster.com", "m.xhamster.com", "xhamster18.com", "xhamster3.com",
        "xhamsterlive.com", "xhamster1.com", "xhamster2.com", "xhamster4.com", "xhamster7.com",
        "xhamsterx.com", "xhamster.desi", "xhamster1.desi", "xhamster2.desi", "xhamsters.com",
        "xhamsterslive.com", "xhamster.pro", "xhamster.club", "xhamster.red", "xhamster.page",
        "xhamstervideo.net", "xhamster.porn", "xhamster.best", "xhamster.videos", "xhamster.cam",
        "xhamster.zone", "xhamster.love", "xhamster.webcam", "xhamsterhd.com", "xhxhamster.com",
        "xxhamster.com", "xhamsterfree.com", "xhamsterpornhub.com", "xhamstertube.com",
        "xhamster.sex", "xhamsterhub.com", "hamsterx.com", "hamsterporn.com",

        // ==== Sexvid.pro Domains ====
        "sexvid.pro", "www.sexvid.pro", "m.sexvid.pro", "sexvidcam.com", "sexvid.site", "sexvid.xxx",
        "sexvid.club", "sexvid.red", "sexvid.video", "sexvid.cam", "sexvid.mobi", "sexvid.vip", "sexvid.live",
        "sexvid.pro.com", "sexvidtube.com", "sexvid123.com", "sexvid1.com", "sexvid.vip", "sexvidpage.com",
        "sexvidmovies.com", "sexvidlive.com", "sexvidshow.com", "sexvidweb.com", "sexvidapp.com",

        // ==== Keywords ====
        "hot video", "hot videos", "sex", "sexy", "porn", "porno", "xxx", "x rated", "x-rated", "hot girl", "hot girls", "nude", 
        "naked", "tits", "milf", "teen sex", "gay sex", "lesbian", "incest", "stepmom", 
        "stepsis", "adult video", "adult site", "erotic", "softcore", "hardcore", "blowjob", "anal", "cum", 
        "creampie", "deepthroat", "masturbate", "masturbation", "hentai", "fap", "bdsm", "fetish", "pussy", 
        "vagina", "dick", "cock", "penis", "orgasm", "threesome", "sex tape", "cam girl", "webcam girl", 
        "onlyfans", "xxx video", "x video", "xx video", "sex video", "xxx video site", "x videos", "xxx videos", 
        "xx videos", "xvideos", "sexvid", "sexvideo", "pornvideo", "porn video", "sexclips", "porno clips", 
        "hardcore video", "sex film", "adult film", "xxx films", "erotic film", "sex clip", "sexfilm", "pornfilm", 
        "hardcore film", "softcore film", "webcam sex", "live sex", "sexchat", "adultchat", "sextape", "pornhub video", 
        "xhamster video", "xvideos video", "pornhub xxx", "pornstar video", "milf video", "teen video", "hot video", 
        "sexy video", "dirty video", "xxxstream", "adultstream", "hardstream", "fetishstream", "sexstream", "adultcams",
        "webcams xxx", "porno live", "adultstreaming", "xvideoslive", "sex on cam", "sex streaming", "live sexcams",
        "sex cams live", "porncams", "porno cams", "xvideoscams", "live xxx cam", "live adult cams", "erotic cams", 
        "xxxcams", "adultstreamingcams", "pornstreaming", "sexcams", "webcamsextreme", "extremecams",

        // ==== Random sites ====
        "youporn.com", "redtube.com", "spankbang.com", "brazzers.com", "youjizz.com", "tnaflix.com",
        "beeg.com", "efukt.com", "porndig.com", "xtube.com", "hclips.com", "motherless.com", "drtuber.com",
        "porntube.com", "fux.com", "bangbros.com", "slutload.com", "fapvid.com", "boobpedia.com",
        "rule34.xxx", "xmissy.nl", "theporndude.com", "hqporner.com", "pornerbros.com", "porn.com",
        "ashemaletube.com", "xozilla.com", "gaytube.com", "tubegalore.com", "voyeurhit.com", "mofosex.com",
        "javhd.com", "momtube.com", "mydirtyhobby.com", "cliphunter.com", "sex.com", "camsoda.com",
        "cam4.com", "livejasmin.com", "chaturbate.com", "bongacams.com", "stripchat.com", "hentaigasm.com",
        "nhentai.net", "hentaifox.com", "hentaihaven.xxx", "3movs.com", "realgfporn.com", "nuvid.com",
        "xxxbunker.com", "lustery.com", "pervclips.com", "tube8.com", "perfectgirls.net", "watchmygf.me",
        "empflix.com", "lubetube.com", "badjojo.com", "milfzr.com", "fuxhq.com", "camwhores.tv",
        "pornmaki.com", "yespornplease.com", "sunporno.com", "onlyfans.com", "pornwild.to",
        "pornrapid.com", "javlibrary.com", "javcl.com", "javmost.com", "javtiful.com", "japanesebeauties.net",
        "watchporn.to", "freepornhq.xxx", "pornhd.com", "xxxstreams.org", "deviantclip.com", "erothots.co",
        "freudbox.com", "fux.xxx", "manojob.com", "letfap.com", "leakgirls.com", "sextvx.com", "czechav.com",
        "ulust.com", "newgrounds.com/portal/view/", "erome.com", "nsfw.xxx", "pornhat.com", "lustylist.com",
        "pleasuregirl.net", "pornpics.com", "rule34.paheal.net", "femdom-tube.com", "vrporn.com",
        "webcam-4you.com", "camsloveaholics.com", "watchersweb.com", "see.xxx", "porn300.com",
        "hentaistream.com", "rule34hentai.net", "adultempire.com", "fakku.net", "javhub.net", "jav.guru",
        "yuvutu.com", "3dxchat.com", "metart.com", "nubiles.net", "naughtyamerica.com", "team-skeet.com",
        "thothub.tv", "freeusefantasy.com", "lustery.com", "hookuphotshot.com", "realitykings.com"
    };

    Handler handler = new Handler();

    // 🤍 Trigger when entering Settings or App Info screen
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        SharedPreferences prefs = getSharedPreferences("blocker_prefs", Context.MODE_PRIVATE);
        boolean shouldBlock = prefs.getBoolean("block_access", true); // default true

        if (event == null || !shouldBlock) return;

        int eventType = event.getEventType();
        String pkgName = event.getPackageName() != null ? event.getPackageName().toString().toLowerCase() : "";
        String clsName = event.getClassName() != null ? event.getClassName().toString() : "";

        // *** For testing *** //
        //Toast.makeText(this, "pkg: " + pkgName + "\ncls: " + clsName, Toast.LENGTH_SHORT).show();
        
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode == null) return;

       
        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            
            // **** M I U I and O T H E R S * A P P * I N F O - B L O C K **** //
            if (
                (pkgName.contains("settings") || pkgName.contains("miui") || pkgName.contains("xos") ||
                pkgName.equals("com.miui.securitycenter") || pkgName.contains("securitycenter") || pkgName.contains("coloros") || pkgName.contains("oppo")) &&
                (clsName.toLowerCase().contains("appinfo") ||
                clsName.equals("com.miui.appmanager.ApplicationsDetailsActivity") ||
                clsName.contains("ApplicationsDetailsActivity") ||
                clsName.toLowerCase().contains("details") ||
                clsName.toLowerCase().contains("applications"))
                ) {
                if (searchNodeForText(rootNode, "WebBlocker") ||
                    searchNodeForText(rootNode, "com.mycompany.websiteblockerbot")) {
                    //delayGoToHome();
                    performGlobalAction(GLOBAL_ACTION_HOME);
                    Toast.makeText(BlockerService.this, "app info triggerd", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            
            // **** H O T S P O T - B L O C K [ FOR FUN/TEST ] **** //
            /*
            if (pkgName.equals("com.android.settings") && clsName.contains("MiuiTetherSettingsActivity") || clsName.contains("WifiTetherSettingsActivity")) {
                performGlobalAction(GLOBAL_ACTION_HOME);
                return;
            }
            
            // **** W I F I - B L O C K [ FOR FUN/TEST ] **** //
            if (pkgName.equals("com.android.settings") && clsName.contains("WifiSettingsActivity") || clsName.contains("WifiSettings2Activity")) {
                performGlobalAction(GLOBAL_ACTION_HOME);
                return;
            }
            */
            
            if (pkgName.equals("com.android.settings") && clsName.contains("HighPowerApplicationsActivity")) {
                performGlobalAction(GLOBAL_ACTION_HOME);
                return;
            }
            
            // ***** 😂😂😂 *****//
            /*
            if (pkgName.equals("com.transsion.XOSLauncher") && clsName.equals("com.android.quickstep.src.com.android.launcher3.uioverrides.QuickstepLauncher")) {
                //performGlobalAction(GLOBAL_ACTION_HOME);
                
                 Toast.makeText(BlockerService.this, "R A N S O M", Toast.LENGTH_SHORT).show();
                 return;
            }
            */
            
            // **** M I U I - A U T O S T A R - B L O C K **** //
            if (pkgName.equals("com.miui.securitycenter") && clsName.contains("AutoStartManagementActivity")) {
                performGlobalAction(GLOBAL_ACTION_HOME);
                Toast.makeText(BlockerService.this, "auto start triggerd", Toast.LENGTH_SHORT).show();
                return;
            } 
            
            // **** A C C E S S I B I L I T Y - B L O C K **** //
            for (CharSequence text : event.getText()) {
                if (text != null && text.toString().toLowerCase().contains("accessibility")) {
                    delayGoToHome();
                    Toast.makeText(BlockerService.this, "app info triggerd from settings", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            
            // **** D E V I C E * A D M I N - B L O C K **** //
            if ((pkgName.contains("settings") || pkgName.contains("miui") || pkgName.contains("coloros")) &&
                (clsName.contains("deviceadmin") || clsName.contains("deviceadminadd") || clsName.contains("adddevice") || 
                clsName.contains("permcenter") || clsName.contains("security") || clsName.contains("admin"))) {
                delayGoToHome();
                Toast.makeText(BlockerService.this, "div admin triggerd", Toast.LENGTH_SHORT).show();
                return;
            }
            
            // **** P U R E * A C C E S S I B I L I T Y * C L A S S [ M I U I  & X O S ] ****//
            if (pkgName.equals("com.android.settings") &&
                clsName.equals("com.android.settings.accessibility.MiuiAccessibilitySettingsActivity") || clsName.equals("com.android.settings.Settings$AccessibilitySettingsActivity")) {
                delayGoToHome();
                Toast.makeText(BlockerService.this, "accessibility triggerd from app", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Optional: Call extra features
        traverseNode(rootNode);
        //handleSettingsBlocking(event);
    }
    
    private boolean searchNodeForText(AccessibilityNodeInfo node, String text) {
        if (node == null || text == null) return false;
        if (node.getText() != null && node.getText().toString().toLowerCase().contains(text.toLowerCase())) {
            return true;
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            if (searchNodeForText(node.getChild(i), text)) {
                return true;
            }
        }
        return false;
    }

    private void traverseNode(AccessibilityNodeInfo node) {
        if (node == null) return;

        CharSequence text = node.getText();
        if (text != null) {
            for (int i = 0; i < blockedSites.length; i++) {
                if (text.toString().contains(blockedSites[i])) {
                    
                    
                    
                    // Check if user is in YouTube app // ** S T A R T ** //
                    AccessibilityNodeInfo root = getRootInActiveWindow();
                    if (root != null && root.getPackageName() != null) {
                        String currentApp = root.getPackageName().toString().toLowerCase();
                        if (currentApp.contains("youtube")) {
                            // Block YouTube search result by going to home screen
                            performGlobalAction(GLOBAL_ACTION_HOME);
                            Toast.makeText(BlockerService.this, "Blocked YouTube search", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    
                    // ** E N D ** //
    
                    blockSite();
                    return;
                }
            }
        }

        for (int i = 0; i < node.getChildCount(); i++) {
            traverseNode(node.getChild(i));
        }
    }

    
    private void blockSite() {
      
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        // Back out after short delay (to exit blocked site)
        handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    performGlobalAction(GLOBAL_ACTION_BACK);
                }
            }, 1000); // Delay to allow tab to open
    }
    
    

    // ************ S T A R T **************//
    
    /*
    
    private void handleSettingsBlocking(AccessibilityEvent event) {
        SharedPreferences prefs = getSharedPreferences("blocker_prefs", MODE_PRIVATE);
        boolean blockAdmin = prefs.getBoolean("block_admin", false);
        
        if (!blockAdmin) return;
      
        String pkg = event.getPackageName() != null ? event.getPackageName().toString().toLowerCase() : "";
        String cls = event.getClassName() != null ? event.getClassName().toString().toLowerCase() : "";

        int type = event.getEventType();
        
        // ❤ Automatically back from admin page      
        if (type == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if ((pkg.contains("settings") || pkg.contains("miui") || pkg.contains("coloros")) &&
                (cls.contains("deviceadmin") || cls.contains("deviceadminadd") || cls.contains("adddevice") || 
                cls.contains("permcenter") || cls.contains("security") || cls.contains("admin"))) {
                delayGoToHome();
                Toast.makeText(BlockerService.this, "div admin triggerd", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        

        // 🤍 Automatically back from accessibility settings
        if (type == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (pkg.equals("com.android.settings") &&
                cls.equals("com.android.settings.accessibility.MiuiAccessibilitySettingsActivity") || cls.equals("com.android.settings.Settings$AccessibilitySettingsActivity")) {
                delayGoToHome();
                Toast.makeText(BlockerService.this, "accessibility triggerd from app", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        
        
        // 💞 Automatically back from MIUI AutoStart settings
        if (type == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (pkg.equals("com.miui.securitycenter") && cls.contains("AutoStartManagementActivity")) {
                performGlobalAction(GLOBAL_ACTION_HOME);
                Toast.makeText(BlockerService.this, "auto start triggerd", Toast.LENGTH_SHORT).show();
                return;
            } 
        }
        
        // HOTSPOT BLOCK FOR FUN/TEST
        if (type == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            // Block hotspot tether settings
            if (pkg.equals("com.android.settings") && cls.contains("WifiTetherSettingsActivity")) {
                performGlobalAction(GLOBAL_ACTION_HOME);
                return;
            }
        }
        

        //️ Extra check for accessibility using content change event // (TYPE_WINDOW_CONTENT_CHANGED)
        if (type == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event.getText() != null) {
            for (CharSequence text : event.getText()) {
                if (text != null && text.toString().toLowerCase().contains("accessibility")) {
                    delayGoToHome();
                    Toast.makeText(BlockerService.this, "app info triggerd from settings", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }
    */
    
    
    //*********** S T O P ***********//
    
    
    private void delayGoToHome() {
        handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                      
                        performGlobalAction(GLOBAL_ACTION_HOME);
                        
                    } catch (Exception e) {
                        Log.e("ACCESS_EVENT", "Error navigating home: " + e.getMessage());
                    }
                }
            }, 100);
    }

    @Override
    public void onInterrupt() {}

    
    @Override
    protected void onServiceConnected() {
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
            | AccessibilityEvent.TYPE_VIEW_SCROLLED
            | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.notificationTimeout = 100;
        
        
        info.packageNames = new String[]{
            
            "com.android.chrome",                  // Google Chrome
            "com.sec.android.app.sbrowser",        // Samsung Internet
            "org.mozilla.firefox",                 // Firefox
            "com.opera.browser",                   // Opera
            "com.opera.mini.native",               // Opera Mini
            "com.microsoft.emmx",                  // Microsoft Edge
            "com.brave.browser",                   // Brave Browser
            "com.kiwibrowser.browser",             // Kiwi Browser
            "com.duckduckgo.mobile.android",       // DuckDuckGo
            "com.vivaldi.browser",                 // Vivaldi
            "org.torproject.torbrowser",           // Tor Browser
            "com.yandex.browser",                  // Yandex Browser
            "com.ecosia.android",                  // Ecosia Browser
            "com.puffin.browser",                  // Puffin Browser
            "com.baidu.browser.apps",              // Baidu Browser
            "com.uc.browser.en",                   // UC Browser (English)
            "com.uc.browser",                      // UC Browser (alt)
            "com.qwant.liberty",                   // Qwant Browser
            "com.z28j.browser",                    // Mint Browser
            "com.apusapps.browser",                // APUS Browser
            "com.rocket.browser",                  // Rocket Browser
            "com.mx.browser",                      // Maxthon Browser
            "com.coolbrowser.browser",             // Cool Browser
            "com.flynx",                           // Flynx Browser
            "acr.browser.barebones",               // Naked Browser
            "com.lightning.browser",               // Lightning Browser
            "org.adblockplus.browser",             // Adblock Browser
            "com.cmcm.armorfly",                   // Armorfly Browser
            "com.lynx.browser",                    // Lynx Browser
            "com.soulbrowser.soulbrowser",         // Soul Browser
            "com.google.android.googlequicksearchbox",
            "com.android.settings",
            "com.google.android.youtube"
            
            
        };
        
        info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS;
        setServiceInfo(info);
        
        super.onServiceConnected();
        
    }
}

