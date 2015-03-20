/*
 * Copyright 2011 Green Luo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package play.modules.trace;

import play.Logger;
import play.Play;
import play.PlayPlugin;
import play.classloading.ApplicationClasses.ApplicationClass;

public class Plugin extends PlayPlugin {
    
    private static boolean enabled_ = false;
    private static String lvl_ = "TRACE";
    private static Enhancer e_ = new Enhancer();
    
    @Override
    public void enhance(ApplicationClass applicationClass) throws Exception {
        if (enabled_) {
            e_.enhanceThisClass(applicationClass);
        }
    }
    
    @Override
    public void onConfigurationRead() {
        enabled_ = Boolean.parseBoolean(Play.configuration.getProperty("trace.enabled", "false"));
        lvl_ = Play.configuration.getProperty("trace.level", "TRACE").toUpperCase();
        enabled_ = enabled_ && !Logger.isEnabledFor(lvl_) ;
    }
    
    public static void log(String message, Object ... args) {
        if ("TRACE".equals(lvl_)) {
            Logger.trace(message, args);
        } else if ("DEBUG".equals(lvl_)) {
            Logger.debug(message, args);
        } else if ("INFO".equals(lvl_)) {
            Logger.debug(message, args);
        } else if ("WARN".equals(lvl_)) {
            Logger.warn(message, args);
        } else if ("ERROR".equals(lvl_)) {
            Logger.error(message, args);
        } else if ("FATAL".equals(lvl_)) {
            Logger.fatal(message, args);
        } else {
            Logger.trace(message, args);
        }
    }
}
