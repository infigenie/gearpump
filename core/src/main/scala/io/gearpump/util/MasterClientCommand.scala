/*
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.gearpump.util

import io.gearpump.cluster.client.{RemoteRuntimeEnvironment, RuntimeEnvironment}
import io.gearpump.util.LogUtil.ProcessType
import io.gearpump.cluster.client.RemoteRuntimeEnvironment
import io.gearpump.util.LogUtil.ProcessType

import scala.util.Try

trait MasterClientCommand extends AkkaApp {

  override def main(args: Array[String]): Unit = {
    RuntimeEnvironment.setRuntimeEnv(new RemoteRuntimeEnvironment)
    LogUtil.loadConfiguration(akkaConfig, ProcessType.CLIENT)

    Try {
      main(akkaConfig, args)
    }.failed.foreach { ex => help(); throw ex }
  }

}
