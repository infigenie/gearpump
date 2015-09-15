/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.gearpump.streaming.examples.wordcount

import io.gearpump.cluster.ClientToMaster.SubmitApplication
import io.gearpump.cluster.MasterToClient.SubmitApplicationResult
import io.gearpump.cluster.{MasterHarness, TestUtil}
import io.gearpump.streaming.examples.wordcountjava.WordCount
import org.scalatest.prop.PropertyChecks
import org.scalatest.{BeforeAndAfter, Matchers, PropSpec}

import scala.concurrent.Future
import scala.util.Success

class WordCountSpec extends PropSpec with PropertyChecks with Matchers with BeforeAndAfter with MasterHarness {

  before {
    startActorSystem()
  }

  after {
    shutdownActorSystem()
  }

  override def config = TestUtil.DEFAULT_CONFIG

  property("WordCount should succeed to submit application with required arguments") {
    val requiredArgs = Array.empty[String]

    val masterReceiver = createMockMaster()

    val args = requiredArgs

    Future {WordCount.main(masterConfig, args)}

    masterReceiver.expectMsgType[SubmitApplication](PROCESS_BOOT_TIME)
    masterReceiver.reply(SubmitApplicationResult(Success(0)))
  }
}
