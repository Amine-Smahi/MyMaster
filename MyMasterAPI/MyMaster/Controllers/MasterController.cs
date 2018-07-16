using System;
using System.IO;
using System.Threading.Tasks;
using Microsoft.ML;
using Microsoft.ML.Data;
using Microsoft.ML.Models;
using Microsoft.ML.Trainers;
using Microsoft.ML.Transforms;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;

namespace MyMaster.Controllers
{
    [Route("api/[controller]")]
    [Produces("application/json")]
    public class MasterController : Controller
    {
        private static string AppPath => Path.GetDirectoryName(Environment.GetCommandLineArgs()[0]);
        private static string TrainDataPath => Path.Combine(AppPath, "datasets", "master-train.txt");
        private static string TestDataPath => Path.Combine(AppPath, "datasets", "master-test.txt");
        private static string ModelPath => Path.Combine(AppPath, "MasterModel.zip");
        

        OutputHelper result =  new OutputHelper("","","","","");

        [HttpPost]
        public OutputHelper Post([FromBody]MasterRequest master)
        {
            Execute(master);
            return result;
        }

        [HttpGet]
        public Master Get()
        {
            return new Master(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
        }

        internal static async Task<PredictionModel<Master, MasterPrediction>> TrainAsync()
        {
            var pipeline = new LearningPipeline
            {
                new TextLoader(TrainDataPath).CreateFrom<Master>(),
                new ColumnConcatenator("Features",
                   "DesignScore",
                   "MathScore",
                   "AlgoScore",
                   "CodingScore",
                   "CliScore",
                   "NetworkingScore",
                   "HardwareScore"),
                new StochasticDualCoordinateAscentClassifier()
            };

            var model = pipeline.Train<Master, MasterPrediction>();
            await model.WriteAsync(ModelPath);

            return model;
        }

        private static void Evaluate(PredictionModel<Master, MasterPrediction> model)
        {
            var testData = new TextLoader(TestDataPath).CreateFrom<Master>();
            var evaluator = new ClassificationEvaluator { OutputTopKAcc = 3 };

            var metrics = evaluator.Evaluate(model, testData);

            for (var i = 0; i < metrics.ConfusionMatrix.Order; i++)
            {
                for (var j = 0; j < metrics.ConfusionMatrix.ClassNames.Count; j++)
                {
                    Console.Write("\t" + metrics.ConfusionMatrix[i, j]);
                }
            }
        }

        public async void Execute(MasterRequest masterRequest)
        {
            if (masterRequest.type == 0)
            {
                Master master = new Master(masterRequest.Label, masterRequest.DesignScore, masterRequest.MathScore,
                  masterRequest.AlgoScore, masterRequest.CodingScore, masterRequest.CliScore, masterRequest.NetworkingScore,
                  masterRequest.HardwareScore);
                var model = await TrainAsync();

                Evaluate(model);
                string evalresult = "";
                var prediction = model.Predict(master);
                evalresult = Evaluate($"{prediction.Score[0]:0.####}", $"{prediction.Score[1]:0.####}",
                  $"{prediction.Score[2]:0.####}", $"{prediction.Score[3]:0.####}");
                result = new OutputHelper(evalresult, $"{prediction.Score[0]:0.####}", $"{prediction.Score[1]:0.####}",
                  $"{prediction.Score[2]:0.####}", $"{prediction.Score[3]:0.####}");
            }
            else
            {
                Console.WriteLine(Directory.GetCurrentDirectory() + @"\datasets\iris-train.txt");
                using (StreamWriter writer = System.IO.File.AppendText(@"c:\temp\MyTest.txt"))
                {
                    writer.WriteLine(masterRequest.Label + "\t" + Beautify(masterRequest.DesignScore.ToString()) + "\t" +
                                     Beautify(masterRequest.MathScore.ToString()) + "\t" +
                                     Beautify(masterRequest.AlgoScore.ToString()) + "\t" +
                                     Beautify(masterRequest.CodingScore.ToString()) + "\t" +
                                     Beautify(masterRequest.CliScore.ToString()) + "\t" +
                                     Beautify(masterRequest.NetworkingScore.ToString()) + "\t" +
                                     Beautify(masterRequest.HardwareScore.ToString()));
                }
            }
        }
        public string Evaluate(string val1,string val2,string val3, string val4)
        {
            if(float.Parse(val1) > float.Parse(val2) && float.Parse(val1) > float.Parse(val3) && float.Parse(val1) > float.Parse(val4))
            {
                return "Aide a la decision et systems intelligents";
            }
            else if (float.Parse(val2) > float.Parse(val1) && float.Parse(val2) > float.Parse(val3) && float.Parse(val2) > float.Parse(val4))
            {
                return "Information industrielle, parallele et embarquée";
            }
            else if (float.Parse(val3) > float.Parse(val1) && float.Parse(val3) > float.Parse(val2) && float.Parse(val3) > float.Parse(val4))
            {
                return "System d'information et technologie web";
            }
            else if (float.Parse(val4) > float.Parse(val1) && float.Parse(val4) > float.Parse(val2) && float.Parse(val4) > float.Parse(val3))
            {
                return "Réseaux et systems distribueés";
            }
            else
            {
                return "It seems we didnt find anything for you";
            }
        }
        public string Beautify(string badFloat)
        {
            string bar  = String.Format("{0:0.00}", Convert.ToDouble(badFloat));
            return bar.ToString();
        }
    }
}
